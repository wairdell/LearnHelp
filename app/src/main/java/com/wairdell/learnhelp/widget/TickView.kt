package com.wairdell.learnhelp.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.animation.AnimatorSet




/**
 *    author : fengqiao
 *    date   : 2021/12/27 17:02
 *    desc   :
 */
class TickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcRect = RectF()
    private var padding = 0F

    var sweepAngle = 0F
        set(value) {
            field = value
            invalidate()
        }

    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var innerRadius = 0F
        set(value) {
            field = value
            invalidate()
        }

    init {
        arcPaint.color = Color.parseColor("#f5d747")
        innerPaint.color = Color.WHITE

        setOnClickListener {
            startAnimation()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        padding = w / 6F / 2
        arcRect.set(padding, padding, w - padding, w - padding)

        innerRadius = w / 2F - padding - 6
    }

    fun startAnimation() {
        innerRadius = width / 2F - padding - 6
        val arcAnimator = ObjectAnimator.ofFloat(this, "sweepAngle", 0F, 360F)
        arcAnimator.duration = 500

        val innerAnimator = ObjectAnimator.ofFloat(this, "innerRadius", innerRadius, 0F)
        innerAnimator.duration = 500

        val scaleAnimator = ObjectAnimator.ofFloat(padding, width / 8F / 2, padding)
        scaleAnimator.duration = 500
        scaleAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            arcRect.set(animatedValue, animatedValue, width - animatedValue, height - animatedValue)
            invalidate()
        }

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(arcAnimator, innerAnimator, scaleAnimator)
        animatorSet.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(arcRect, 0F, sweepAngle, true, arcPaint)
        canvas.drawCircle(width / 2F, height / 2F, innerRadius, innerPaint)
    }

}