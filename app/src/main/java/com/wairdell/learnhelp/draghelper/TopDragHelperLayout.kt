package com.wairdell.learnhelp.draghelper

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.customview.widget.ViewDragHelper

class TopDragHelperLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    companion object {
        var TAG = "TopDragHelperLayout"
    }

    var viewDragHelper: ViewDragHelper? = null

    var contentView: TextView? = null
    var topView: TextView? = null

    init {
        contentView = TextView(context)
        addView(
            contentView,
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        contentView?.setBackgroundColor(Color.parseColor("#00FF00"))

        topView = TextView(context)
        addView(
            topView,
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                300
            )
        )
        topView?.setBackgroundColor(Color.parseColor("#FF0000"))

        var helperCallback = object : ViewDragHelper.Callback() {

            var currentTop: Int = 0

            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                Log.d(TAG, "tryCaptureView() called with: child = [$child], pointerId = [$pointerId]")
                return child == topView
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                Log.d(TAG, "clampViewPositionVertical() called with: child = [$child], top = [$top], dy = [$dy]")
                currentTop = Math.min(0, top)
                return currentTop
            }

            override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
                Log.d(TAG, "onEdgeDragStarted() called with: edgeFlags = [$edgeFlags], pointerId = [$pointerId]")
                viewDragHelper?.captureChildView(topView!!, pointerId)
            }

            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                Log.d(
                    TAG,
                    "onViewReleased() called with: releasedChild = [$releasedChild], xvel = [$xvel], yvel = [$yvel]"
                )
                super.onViewReleased(releasedChild, xvel, yvel)
                topView?.also {
                    when {
                        yvel > 300 -> viewDragHelper?.settleCapturedViewAt(0, 0)
                        yvel < -300 -> viewDragHelper?.settleCapturedViewAt(0, -it.measuredHeight)
                        currentTop > -it.measuredHeight / 2 -> viewDragHelper?.settleCapturedViewAt(0, 0)
                        else -> viewDragHelper?.settleCapturedViewAt(0, -it.measuredHeight)
                    }
                    invalidate()
                }
            }
        }
        viewDragHelper = ViewDragHelper.create(this, helperCallback)
        viewDragHelper?.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper?.shouldInterceptTouchEvent(ev) ?: super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewDragHelper?.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (viewDragHelper?.continueSettling(true) == true) {
            invalidate()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        contentView?.run {
            layout(0, 0, measuredWidth, measuredHeight)
        }
        topView?.layout(0, (topView?.measuredHeight ?: 0) * -1, topView?.measuredWidth ?: 0, 0)
    }


}