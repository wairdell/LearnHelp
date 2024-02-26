package com.wairdell.learnhelp.dispatch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 *    author : fengqiao
 *    date   : 2023/2/13 13:53
 *    desc   :
 */
class DispatchViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        return super.onInterceptTouchEvent(ev)
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i("TAG", "viewGroup => onTouchEvent: " + event.action)
        return super.onTouchEvent(event)
    }

}