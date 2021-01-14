package com.wairdell.learnhelp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class LayoutWidget(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    private val showText = "伊尔凡当时的发放第三方刚刚发生的股份第三个是否法第三方士大夫"

    private val mLayout: Layout

    private val mPaint: TextPaint = TextPaint()

    private val textWidth = 800

    constructor(context: Context?, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context?): this(context, null)

    init {
        mPaint.color = Color.RED
        mPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14f, resources.displayMetrics)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mLayout = StaticLayout.Builder.obtain(showText, 0, showText.length, mPaint, textWidth).build()
        } else {
            mLayout = StaticLayout(showText, mPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 0f, 1f, false)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, mLayout.height)
        } else if(widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
            )
        } else if(heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), mLayout.height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate((width - textWidth) / 2f, (height - mLayout.height) / 2f)
        mLayout.draw(canvas)
        canvas.translate(0f, 0f)

    }

}