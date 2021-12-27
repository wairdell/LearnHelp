package com.wairdell.learnhelp.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *    author : fengqiao
 *    date   : 2021/12/27 14:32
 *    desc   :
 */
class CheckView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val line1 : FloatArray = floatArrayOf(7F, 15F, 14F, 21F)
    private val line2 : FloatArray = floatArrayOf(14F, 21F, 24F, 10F)

    var progress : Float = 1F
        set(value) {
            field = value
            invalidate()
        }

    init {
        bgPaint.style = Paint.Style.FILL

        linePaint.style = Paint.Style.FILL
        linePaint.strokeWidth = 3F
        linePaint.color = Color.parseColor("#B96400")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val animator = ObjectAnimator.ofFloat(this, "progress", 0F, 1F)
        animator.duration = 1 * 1000
        animator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgPaint.shader = LinearGradient(w / 2F, 0F, w / 2F, h.toFloat(), Color.parseColor("#FFDA47"), Color.parseColor("#F2AB3C"), Shader.TileMode.CLAMP )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2F, height / 2F, width.coerceAtMost(height) / 2F, bgPaint)
        if (progress < 0.5) {
            canvas.drawLine(line1[0], line1[1], line1[0] + (line1[2] - line1[0]) * progress * 2, line1[1] + (line1[3] - line1[1]) * progress * 2, linePaint)
        } else {
            val step2Progress = (progress - 0.5F) * 2
            canvas.drawLines(line1, linePaint)
            val startX = line2[0]
            val startY = line2[1]
            canvas.drawLine(startX,
                startY, startX + (line2[2] - startX) * step2Progress, startY + (line2[3] - startY) * step2Progress, linePaint)
        }
        //canvas.drawLine(14F, 24F, 26F, 10F, linePaint)
    }

}