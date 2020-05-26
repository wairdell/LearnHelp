package com.wairdell.learnhelp.xfermode

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View

class XfermodeDisplayView(context: Context?, attrs: AttributeSet?) : View(context, attrs)  {

    var mPaint: Paint = Paint()

    var xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    var textPaint: Paint = Paint()

    var mode: PorterDuff.Mode = PorterDuff.Mode.DST_IN
        set(value) {
            field = value
            xfermode = PorterDuffXfermode(field)
        }

    constructor(context: Context?) : this(context, null)

    init {
        textPaint.color = Color.RED
        textPaint.textSize = 30f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerId : Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        else
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        mPaint.color = Color.parseColor("#00FF00")
        var side = width / 3f * 2
        canvas.drawOval(RectF(0f, 0f, side, side), mPaint)


        mPaint.color = Color.parseColor("#0000FF")
        mPaint.xfermode = xfermode
        var start = width / 3f
        canvas.drawRect(start, start, width.toFloat(), width.toFloat(), mPaint)
        mPaint.xfermode = null

        var textWidth = textPaint.measureText(mode.name)
        canvas.drawText(mode.name, (width - textWidth) / 2f, (height - 30) / 2f, textPaint)
        canvas.restoreToCount(layerId)
    }



}