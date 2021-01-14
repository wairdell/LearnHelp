package com.wairdell.learnhelp.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.RelativeLayout
import com.wairdell.learnhelp.px2dp

class AdjustRelativeLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs) {

    companion object {
        val TAG: String = AdjustRelativeLayout::class.java.simpleName
    }

    constructor(context: Context) : this(context, null, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mode = MeasureSpec.getMode(heightMeasureSpec)
        Log.d(TAG, "measuredHeight = $measuredHeight")
        if (mode == MeasureSpec.AT_MOST) {
            var newHeightMeasureSpec = heightMeasureSpec
            var size = minimumHeight
            var ruler = minimumHeight / 2
            if(measuredHeight % ruler != 0) {
                size = (measuredHeight / ruler) * ruler + ruler
                newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
                super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
            }
            Log.d(TAG, "onMeasure() mode = $mode , size = ${px2dp(size)} }")
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "onLayout Height = ${px2dp(bottom - top)}")
    }



}