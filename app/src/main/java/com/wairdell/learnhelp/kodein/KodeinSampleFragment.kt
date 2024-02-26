package com.wairdell.learnhelp.kodein

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.wairdell.learnhelp.common.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class KodeinSampleFragment : Fragment(), KodeinAware {

    companion object {
        val TAG = KodeinSampleFragment::class.java.simpleName
    }

    protected val parentKodein by closestKodein()
    override val kodeinContext = kcontext(this)
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
    }
    private val viewModel : KodinSampleViewModel by instance()

    private val lifecycleObserver = object : LifecycleEventObserver {

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            Log.d(TAG, "onStateChanged() called with: source = $source, event = $event")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() viewModel = $viewModel")
        var tempViewModel = ViewModelProvider(this, ViewModelFactory)[KodinSampleViewModel::class.java]
        Log.d(TAG, "onCreate() tempViewModel1 = $tempViewModel")
        tempViewModel = ViewModelProvider(this, ViewModelFactory)[KodinSampleViewModel::class.java]
        Log.d(TAG, "onCreate() tempViewModel2 = $tempViewModel")
        lifecycle.addObserver(lifecycleObserver)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = FrameLayout(requireContext())
        rootView.layoutParams = ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        rootView.setBackgroundColor(Color.parseColor("#FF0000"))
        return rootView
    }

    override fun onDestroy() {
        lifecycle.removeObserver(lifecycleObserver)
        Log.d(TAG, "onDestroy() called")
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach() called with: context = $context")
        super.onAttach(context)
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach() called")
        super.onDetach()
    }


}