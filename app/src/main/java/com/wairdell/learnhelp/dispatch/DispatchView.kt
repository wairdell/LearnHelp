package com.wairdell.learnhelp.dispatch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 *    author : fengqiao
 *    date   : 2023/2/13 13:55
 *    desc   :
 */
class DispatchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i("TAG", "view => onTouchEvent: " + event.action)
        super.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        return false
    }
}