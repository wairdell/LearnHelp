package com.wairdell.learnhelp.draghelper

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper

class SimpleDragHelperView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    companion object {
        var TAG = this::class.java.simpleName
    }

    var viewDragHelper : ViewDragHelper? = null

    constructor(context: Context) : this(context, null)

    init {
        var callback = object : ViewDragHelper.Callback() {

            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                Log.d(TAG, "tryCaptureView() called with: child = [$child], pointerId = [$pointerId]")
                return true
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                Log.d(TAG, "clampViewPositionHorizontal() called with: child = [$child], left = [$left], dx = [$dx]")
                //return super.clampViewPositionHorizontal(child, left, dx)
                return left
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                Log.d(TAG, "clampViewPositionVertical() called with: child = [$child], top = [$top], dy = [$dy]")
                return top
                //return super.clampViewPositionVertical(child, top, dy)
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                Log.d(TAG, "onViewReleased() called with: releasedChild = [$releasedChild], xvel = [$xvel], yvel = [$yvel]")
                super.onViewReleased(releasedChild, xvel, yvel)
            }

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                Log.d(TAG, "onEdgeDragStarted() called with: edgeFlags = [$edgeFlags], pointerId = [$pointerId]")
                super.onEdgeDragStarted(edgeFlags, pointerId)
            }
        }
        viewDragHelper = ViewDragHelper.create(this, callback)
        viewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_BOTTOM)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper?.shouldInterceptTouchEvent(ev) ?: super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper?.processTouchEvent(event)
        return true
    }

}