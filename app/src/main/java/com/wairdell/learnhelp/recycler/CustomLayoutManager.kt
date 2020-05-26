package com.wairdell.learnhelp.recycler

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.Utils

class CustomLayoutManager(
    var context: Context, @RecyclerView.Orientation orientation: Int,
    reverseLayout: Boolean
) : LinearLayoutManager(context, orientation, reverseLayout) {

    companion object {
        const val TAG = "CustomLayoutManager"
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //return super.generateDefaultLayoutParams()
        Log.d(TAG, "generateDefaultLayoutParams() called")
        return RecyclerView.LayoutParams(
            Utils.getScreenWidth(context) / 3,
            ViewGroup.LayoutParams.WRAP_CONTENT

        )
    }

    override fun generateLayoutParams(c: Context, attrs: AttributeSet) : RecyclerView.LayoutParams? {
        Log.d(TAG, "generateLayoutParams() called with: attrs = [$attrs]")
        var layoutParams = super.generateLayoutParams(c, attrs)
        layoutParams.width = Utils.getScreenWidth(context) / 2
        return layoutParams
    }

}