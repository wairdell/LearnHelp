package com.wairdell.learnhelp.recycler

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LooperLayoutManager : RecyclerView.LayoutManager() {

    companion object {
        const val TAG = "LooperLayoutManager"
    }

    var looperEnable: Boolean = true

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun canScrollVertically(): Boolean {
        return super.canScrollVertically()
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)
        Log.d(TAG, "onLayoutChildren() called with: recycler = [$recycler], state = [$state]")
        if (itemCount <= 0) {
            return
        }
        //如果当前时准备状态，直接返回
        if (state.isPreLayout) {
            return
        }
        //将视图分离放入scrap缓存中，以准备重新对view进行排版
        detachAndScrapAttachedViews(recycler)

        var autualWidth = 0
        for (i in 0..itemCount) {
            var itemView = recycler.getViewForPosition(i)
            addView(itemView)
            //测量itemView的宽高
            measureChildWithMargins(itemView, 0, 0)
            var itemWidth = getDecoratedMeasuredWidth(itemView)
            var itemHeight = getDecoratedMeasuredHeight(itemView)
            //根据itemView的宽高进行布局
            layoutDecorated(itemView, autualWidth, 0, autualWidth + itemWidth, itemHeight)
            autualWidth += itemWidth
            //如果当前布局过的itemView的宽度总和大于RecyclerView的宽，则不再进行布局
            if (autualWidth > width) {
                break
            }
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?): Int {
        Log.d(TAG, "scrollHorizontallyBy() called with: dx = [$dx], recycler = [$recycler], state = [$state]")
        var travl = fill(dx, recycler, state)
        if (travl == 0) {
            return 0
        }
        offsetChildrenHorizontal(-travl)
        recyclerHideView(dx, recycler, state)
        return travl
    }

    fun fill(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?): Int {
        var resultDx: Int = dx
        if (dx > 0) {
            var lastView: View = getChildAt(childCount - 1) ?: return 0
            var lastPos = getPosition(lastView)
            //可见的最后一个itemView完全滑进来了，需要补充新的
            if (lastView.right <= width) {
                var scrap: View? = null
                if (lastPos == itemCount - 1) {
                    if (looperEnable) {
                        scrap = recycler.getViewForPosition(0)
                    } else {
                        resultDx = 0
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1)
                }
                if (scrap == null) {
                    return resultDx
                }
                addView(scrap)
                measureChildWithMargins(scrap, 0, 0)
                scrap.let {
                    var itemWidth = getDecoratedMeasuredWidth(it)
                    var itemHeight = getDecoratedMeasuredHeight(it)
                    layoutDecorated(it, lastView.right, 0, lastView.right + itemWidth, itemHeight)
                }
                return resultDx
            }

        } else {
            var firstView: View = getChildAt(0) ?: return 0
            var firstPos = getPosition(firstView)
            if(firstView.left <= 0) {
                var scrap: View? = null
                if(firstPos == 0) {
                    if(looperEnable) {
                        scrap = recycler.getViewForPosition(itemCount - 1)
                    } else {
                        resultDx = 0
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1)
                }
                if(scrap == null) {
                    return resultDx
                }
                addView(scrap, 0)
                measureChildWithMargins(scrap, 0, 0)
                scrap.let {
                    var itemWidth = getDecoratedMeasuredWidth(it)
                    var itemHeight = getDecoratedMeasuredHeight(it)
                    layoutDecorated(it, firstView.left - itemWidth, 0, firstView.left, itemHeight)
                }
                return resultDx
            }
        }
        return dx
    }

    fun recyclerHideView(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        for (i in 0..itemCount) {
            var view = getChildAt(i) ?: continue
            if(dx > 0) {
                if(view.right < 0) {
                    removeAndRecycleView(view, recycler)
                }
            } else {
                if(view.left > width) {
                    removeAndRecycleView(view, recycler)
                }
            }
        }
    }
}