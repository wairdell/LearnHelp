package com.wairdell.learnhelp.bessel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View

class BesselAnimationView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var fixedPaint: Paint = Paint()

    var besselPaint: Paint = Paint()

    var tempPaint: Paint = Paint()

    var t = 0

    var valueAnimator: ValueAnimator? = null

    private var startX = 0f
    private var startY = 0f

    private var controlX = 0f
    private var controlY = 0f

    private var endX = 0f
    private var endY = 0f

    private var tempStartX = 0f
    private var tempStartY = 0f

    private var tempEndX = 0f
    private var tempEndY = 0f

    private val besselPath: Path = Path()

    constructor(context: Context?) : this(context, null)

    init {
        fixedPaint.color = Color.parseColor("#999999")
        fixedPaint.strokeWidth = 5f
        fixedPaint.isAntiAlias = true

        besselPaint.color = Color.parseColor("#FF0000")
        besselPaint.style = Paint.Style.STROKE
        besselPaint.strokeWidth = 5f
        besselPaint.isAntiAlias = true

        tempPaint.color = Color.parseColor("#00FF00")
        tempPaint.strokeWidth = 5f
        tempPaint.textSize = resources.displayMetrics.density * 15 + 0.5f
        tempPaint.isAntiAlias = true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        startAnimation()
        startX = 0f
        startY = height.toFloat() / 2
        controlX = width.toFloat() / 2
        controlY = 0f
        endX = width.toFloat()
        endY = height.toFloat() / 2

        besselPath.moveTo(startX, startY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawLine(startX, startY, controlX, controlY, fixedPaint)
        canvas?.drawLine(controlX, controlY, endX, endY, fixedPaint)

        canvas?.drawLine(tempStartX, tempStartY, tempEndX, tempEndY, tempPaint)
        canvas?.drawPath(besselPath, besselPaint)

        var text = "$t%"
        canvas?.drawText(text, (width - tempPaint.measureText(text)) / 2, (height / 2).toFloat(), tempPaint)
    }

    fun startAnimation() {
        if (valueAnimator != null) {
            valueAnimator?.cancel()
        }
        var valueAnimator = ValueAnimator.ofInt(0, 100)
        valueAnimator.duration = 3 * 1000
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener {
            t = it.animatedValue as Int
            tempStartX = startX + (controlX - startX) * t / 100
            tempStartY = startY + (controlY - startY) * t / 100
            tempEndX = controlX + (endX - controlX) * t / 100
            tempEndY = controlY + (endY - controlY) * t / 100
            besselPath.lineTo(tempStartX + (tempEndX - tempStartX) * t / 100, tempStartY + (tempEndY - tempStartY) * t / 100)
            invalidate()
        })
        valueAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationRepeat(animation: Animator) {
                super.onAnimationRepeat(animation)
                besselPath.reset()
                besselPath.moveTo(startX, startY)
            }
        })
        valueAnimator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (valueAnimator != null) {
            valueAnimator?.cancel()
        }
    }


}