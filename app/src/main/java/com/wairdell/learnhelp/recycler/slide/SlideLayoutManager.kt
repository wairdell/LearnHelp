package com.wairdell.learnhelp.recycler.slide

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.core.view.MotionEventCompat


class SlideLayoutManager(mRecyclerView: RecyclerView, itemTouchHelper : ItemTouchHelper) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        detachAndScrapAttachedViews(recycler)
        if (itemCount > ItemConfig.DEFAULT_SHOW_ITEM) {
            for (position in ItemConfig.DEFAULT_SHOW_ITEM downTo 0) {
                var view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                var widthSpec = width - getDecoratedMeasuredWidth(view)
                var heightSpec = height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(
                    view,
                    widthSpec / 2,
                    heightSpec / 2,
                    widthSpec / 2 + getDecoratedMeasuredWidth(view),
                    heightSpec / 2 + getDecoratedMeasuredHeight(view)
                )
                if (position == ItemConfig.DEFAULT_SHOW_ITEM) {
                    var scale = 1 - (position - 1) * ItemConfig.DEFAULT_SCALE
                    view.scaleX = scale
                    view.scaleY = scale
                    view.translationY =
                        ((position - 1) * view.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y).toFloat()
                } else if (position > 0) {
                    var scale = 1 - position * ItemConfig.DEFAULT_SCALE
                    view.scaleX = scale
                    view.scaleY = scale
                    view.translationY = (position * view.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y).toFloat()
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }
        } else {
            for(position in itemCount - 1 downTo 0) {
                var view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                var widthSpec = width - getDecoratedMeasuredWidth(view)
                var heightSpec = height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(
                    view,
                    widthSpec / 2,
                    heightSpec / 2,
                    widthSpec / 2 + getDecoratedMeasuredWidth(view),
                    heightSpec / 2 + getDecoratedMeasuredHeight(view)
                )
                if(position > 0) {
                    var scale = 1 - position * ItemConfig.DEFAULT_SCALE
                    view.scaleX = scale
                    view.scaleY = scale
                    view.translationY = (position * view.measuredHeight / ItemConfig.DEFAULT_TRANSLATE_Y).toFloat()
                } else {
                    view.setOnTouchListener(mOnTouchListener)
                }
            }


        }
    }

    private val mOnTouchListener = View.OnTouchListener { v, event ->
        val childViewHolder = mRecyclerView.getChildViewHolder(v)
        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
            itemTouchHelper.startSwipe(childViewHolder!!)
        }
        false
    }

}