package com.wairdell.learnhelp.recycler

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.wairdell.learnhelp.randomColor
import kotlin.math.max
import kotlin.math.min

/**
 *    date   : 2024/2/26 14:31
 *    desc   :
 */
class StickyItemDecoration : ItemDecoration() {

    companion object {
        val TAG = StickyItemDecoration::class.java.simpleName
    }

    private val bounds = Rect()
    private val paint = Paint().apply {
        textSize = 26f
    }
    private val sparseArray = SparseIntArray()

    private fun getRandomColor(position: Int): Int {
        var color = sparseArray.get(position)
        if (color == 0) {
            color = randomColor()
            sparseArray.put(position, color)
        }
        return color
    }

    /**
     * itemView上方位置多出来了间隔位置
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition % 3 == 0) {
            outRect.set(0, 150, 0, 0)
        } else {
            outRect.set(0, 5, 0, 0)
        }
    }

    /**
     * 同位置绘制会被itemView覆盖
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        Log.d(TAG, "onDraw")
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childAt = parent.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(childAt)
            parent.getDecoratedBoundsWithMargins(childAt, bounds)
            Log.e("TAG", "${bounds}")
//            bounds.bottom = bounds.top
            bounds.bottom = bounds.top + (if (adapterPosition % 3 == 0) 150 else 5)
            paint.color = getRandomColor(adapterPosition)
            c.drawRect(bounds, paint)
            paint.color = Color.BLACK
            c.drawText((adapterPosition / 3).toString(), 0F, bounds.top.toFloat() + 50, paint)
        }
    }

    /**
     * 覆盖绘制itemView
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        Log.d(TAG, "onDrawOver")
        val childAt = parent.getChildAt(0)
        val adapterPosition = parent.getChildAdapterPosition(childAt)
        val nextPosition = adapterPosition + 1
        parent.getDecoratedBoundsWithMargins(childAt, bounds)
        if (adapterPosition / 3 != nextPosition / 3) {
            bounds.bottom = min(150, bounds.bottom)
            bounds.top = bounds.bottom - 150
        } else {
            bounds.top = 0
            bounds.bottom = 150
        }
        paint.color = getRandomColor((adapterPosition / 3) * 3)
        c.drawRect(bounds, paint)
        paint.color = Color.BLACK
        c.drawText((adapterPosition / 3).toString(), 0F, bounds.top.toFloat() + 50, paint)
    }

}