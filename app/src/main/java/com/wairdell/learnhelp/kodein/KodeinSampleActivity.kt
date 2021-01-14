package com.wairdell.learnhelp.kodein

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.common.ViewModelFactory
import kotlinx.android.synthetic.main.activity_kodein_sample.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.closestKodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.*
import java.util.concurrent.ExecutorService

class KodeinSampleActivity : AppCompatActivity(), KodeinAware {

    companion object {
        val TAG = KodeinSampleActivity::class.java.simpleName
    }

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<AppCompatActivity>(this)

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        bind<KodinSampleViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
            ViewModelProvider(context, ViewModelFactory)[KodinSampleViewModel::class.java]
        }
    }

    val executorService: ExecutorService by instance()

    val kodeinSampleFragment = KodeinSampleFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kodein_sample)
        btn_start.setOnClickListener {
            for (i in 0..3) {
                executorService.execute {
                    for (i in 0..4) {
                        Log.d(TAG, "i = $i")
                    }
                }
            }
        }

        btn_switch_fragment.setOnClickListener {
            var findFragmentByTag = supportFragmentManager.findFragmentByTag(KodeinSampleFragment.TAG)
            var transaction = supportFragmentManager.beginTransaction()
            if(findFragmentByTag == null) {
                transaction.add(R.id.fl_container, kodeinSampleFragment, KodeinSampleFragment.TAG)
            } else {
                transaction.remove(findFragmentByTag)
            }
            transaction.commit()
        }
    }


}