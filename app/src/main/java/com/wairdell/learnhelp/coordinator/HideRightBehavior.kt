package com.wairdell.learnhelp.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

class HideRightBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var directionChange = 0

    /*override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return super.onDependentViewChanged(parent, child, dependency)
    }*/

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray
    ) {
        if(dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
            child.animate().cancel()
            directionChange = 0
        }
        directionChange += dy
        if(directionChange > child.width) {
            val animator = child.animate().translationX(child.width.toFloat())
            animator.duration = 500
            animator.start()
        } else if(directionChange < 0) {
            val animator = child.animate().translationX(0f)
            animator.duration = 500
            animator.start()
        }
    }


}