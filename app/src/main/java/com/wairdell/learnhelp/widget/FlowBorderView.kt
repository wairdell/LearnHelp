package com.wairdell.learnhelp.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.sqrt

/**
 *    author : fengqiao
 *    date   : 2022/11/1 15:10
 *    desc   :
 */
class FlowBorderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val borderRect: RectF = RectF()
    private val clipPath: Path = Path()
    private val borderWith = 5F
    private var rotateAnimator: Animator? = null
    private var rotateDegrees = 0F
        private set(value) {
            field = value
            invalidate()
        }

    init {
//        paint.color = Color.RED
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val halfWidth = w / 2F
        val halfHeight = h / 2F
        val side = sqrt((halfWidth * halfWidth + halfHeight * halfHeight).toDouble()).toFloat()
        borderRect.set(halfWidth, halfHeight, halfWidth + side, halfHeight + side)
        clipPath.reset()
        clipPath.addRect(0F, 0F, w.toFloat(), h.toFloat(), Path.Direction.CW)
        clipPath.addRect(borderWith, borderWith, w - borderWith, h - borderWith, Path.Direction.CCW)
        paint.shader = SweepGradient(halfWidth / 2, halfHeight / 2, Color.parseColor("#FFC328"), Color.parseColor("#E20000"))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        rotateAnimator = ObjectAnimator.ofFloat(this, "rotateDegrees", 0F, 359F).apply {
            duration = 2_500L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        rotateAnimator?.cancel()
        rotateAnimator = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.clipPath(clipPath)
        canvas.rotate(rotateDegrees, width / 2F, height / 2F)
        canvas.drawRect(borderRect, paint)
        canvas.rotate(-rotateDegrees, width / 2F, height / 2F)
        canvas.restore()
    }

}