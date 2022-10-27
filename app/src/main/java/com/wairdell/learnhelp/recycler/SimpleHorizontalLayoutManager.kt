package com.wairdell.learnhelp.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SimpleHorizontalLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        //如果 itemCount 为0时没有任何 itemView 直接返回
        if (itemCount <= 0) {
            return
        }
        if (state.isPreLayout) {
            return
        }
        //将现在Attached到RecyclerView的移除回收
        detachAndScrapAttachedViews(recycler)
        var useWidth = 0
        for (i in 0..itemCount) {
            //获取某个position的itemView
            val childView = recycler.getViewForPosition(i)
            //添加进recyclerView
            addView(childView)
            //测量itemView
            measureChildWithMargins(childView, 0, 0)
            val childViewWidth = getDecoratedMeasuredWidth(childView)
            val childViewHeight = getDecoratedMeasuredHeight(childView)
            //根据itemView测量的宽高布局
            layoutDecorated(childView, useWidth, 0, useWidth + childViewWidth, childViewHeight)
            useWidth += childViewWidth
            //如果itemView布局已超出recycleView的宽度就不在添加
            if (useWidth >= width) {
                break
            }
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        var resultDx = 0
        //判断是左滑还是右滑,小于0是左滑
        if (dx < 0) {
            //获取当前recyclerView中第一个itemView
            val childView = getChildAt(0) ?: return 0
            val childViewPosition = getPosition(childView)
            //如果第一itemView的position为0时，则没有前一个元素
            if (childViewPosition == 0) {
                resultDx = if (childView.left < 0) Math.max(childView.left, dx) else 0
            } else if (childView.left < 0) {
                //还在recycler的可以范围内不用加载
                resultDx = dx
            } else {
                //不为0，则有前一个元素，获取前一个位置的itemView
                val newChildView = recycler.getViewForPosition(childViewPosition - 1)
                //添加到recyclerView第一个位置
                addView(newChildView, 0)
                measureChildWithMargins(newChildView, 0, 0)
                val newChildViewWidth = getDecoratedMeasuredWidth(newChildView)
                val newChildViewHeight = getDecoratedMeasuredHeight(newChildView)
                //根据新的itemView测量宽高将itemView布局到之前一个itemView的左边
                layoutDecoratedWithMargins(
                    newChildView,
                    childView.left - newChildViewWidth,
                    0,
                    childView.left,
                    newChildViewHeight
                )
                resultDx = dx
            }
        } else {
            val childView = getChildAt(childCount - 1) ?: return 0
            val childViewPosition = getPosition(childView)
            //如果第一itemView的position为最后一个，则没有下一个元素
            if (childViewPosition == itemCount - 1) {
                //限制最后一个滑动位置
                resultDx = if(childView.right >= width) Math.min(childView.right - width, dx) else 0
            } else if (childView.right >= width) {
                //这里是如果可视范围还在RecyclerView内就不用加载下一个
                resultDx = dx
            } else {
                //不为最后一个，则有下一个元素，获取下一个位置的itemView
                val newChildView = recycler.getViewForPosition(childViewPosition + 1)
                //添加到recyclerView最后面
                addView(newChildView)
                measureChildWithMargins(newChildView, 0, 0)
                val newChildViewWidth = getDecoratedMeasuredWidth(newChildView)
                val newChildViewHeight = getDecoratedMeasuredHeight(newChildView)
                //根据新的itemView测量宽高将itemView布局到之前一个itemView的右面
                layoutDecoratedWithMargins(
                    newChildView,
                    childView.right,
                    0,
                    childView.right + newChildViewWidth,
                    newChildViewHeight
                )
                resultDx = dx
            }
        }
        recyclerView(dx, recycler)
        offsetChildrenHorizontal(-resultDx)
        return resultDx
    }

    private fun recyclerView(dx: Int, recycler: RecyclerView.Recycler) {
        if (dx <= 0) {
            for (i in 0..childCount) {
                val childView = getChildAt(i) ?: continue
                if (childView.left > width) {
                    removeAndRecycleView(childView, recycler)
                }
            }
        } else {
            for(i in 0..childCount) {
                val childView = getChildAt(i) ?: continue
                if(childView.right < 0) {
                    removeAndRecycleView(childView, recycler)
                }
            }
        }
    }

    //可以水平滑动
    override fun canScrollHorizontally(): Boolean {
        return true
    }
}