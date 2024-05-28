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

    private var isIntercept = false
    private var dispatchCount = 0
    private var touchCount = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            dispatchCount = 0
            touchCount = 0
        }
        Log.i("Dispatch", "DispatchViewGroup => dispatchTouchEvent ${ev.action}  ${++dispatchCount}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.i("Dispatch", "DispatchViewGroup => onInterceptTouchEvent => ${ev.action}")
        /*if (ev.action == MotionEvent.ACTION_DOWN) {
            isIntercept = false
        } else  {
            if (isIntercept) {
                isIntercept = false
                return true
            }
            isIntercept = true
        }*/
//        return super.onInterceptTouchEvent(ev)
        return ev.action != MotionEvent.ACTION_DOWN
//        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i("Dispatch", "DispatchViewGroup => onTouchEvent: ${event.action} ${++touchCount}")
        if (event.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        return super.onTouchEvent(event)
    }

}