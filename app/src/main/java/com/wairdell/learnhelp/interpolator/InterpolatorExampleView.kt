package com.wairdell.learnhelp.interpolator

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Interpolator

class InterpolatorExampleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    constructor(context: Context?) : this(context, null)

    var linePaint: Paint

    var pathPaint: Paint

    var interpolatorPath: Path

    var endX: Float

    var lastTime: Long

    var lastX: Float

    var interpolator: Interpolator? = null

    var speedPath: Path
    var speedPaint: Paint

    var textPaint: Paint

    init {
        linePaint = Paint()
        linePaint.color = Color.parseColor("#FF0000")
        linePaint.strokeWidth = 3f
        endX = 0F
        pathPaint = Paint()
        pathPaint.color = Color.parseColor("#0000FF")
        pathPaint.style = Paint.Style.STROKE
        pathPaint.isAntiAlias = true
        interpolatorPath = Path()
        lastTime = 0
        lastX = 0f

        speedPath = Path()
        speedPaint = Paint()
        speedPaint.color = Color.parseColor("#00FF00")
        speedPaint.style = Paint.Style.STROKE
        speedPaint.strokeWidth = 3f
        speedPaint.isAntiAlias = true

        textPaint = Paint()
        textPaint.color = Color.parseColor("#FF0000")
        textPaint.textSize = resources.displayMetrics.density * 12 + 0.5f
        textPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f, (height / 2).toFloat(), endX, (height / 2).toFloat(), linePaint)

        canvas?.drawPath(interpolatorPath, pathPaint)

        canvas?.drawPath(speedPath, speedPaint)

        canvas?.drawText(interpolator?.javaClass?.simpleName ?: "", 0f, textPaint.textSize, textPaint)
    }

    open fun startAni() {
        endX = 0f
        lastX = 0f
        lastTime = 0
        interpolatorPath.reset()
        speedPath.reset()
        speedPath.moveTo(0f, height * 0.5f)
        //interpolatorPath.moveTo(0f, height.toFloat())
        var valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, width.toFloat())
        valueAnimator.duration = 3000
        valueAnimator.start()
        interpolator.let {
            valueAnimator.interpolator = it
        }
        valueAnimator.addUpdateListener {
            var toX = it.animatedValue as Float
            Log.e("InterpolatorExampleView", "animatedValue = $toX currentPlayTime = ${it.currentPlayTime}")
            var x = (width * it.currentPlayTime / it.duration).toFloat()
            if (it.currentPlayTime - lastTime >= 300) {
                var y = height / 2 - (toX - lastX) / width * height
                if (lastTime == 0L) {
                    interpolatorPath.moveTo(x, y)
                } else {
                    interpolatorPath.lineTo(x, y)
                }
                lastTime = it.currentPlayTime
                lastX = toX
            }
            speedPath.lineTo(x, height * 0.5f - toX / width * height * 0.5f)
            endX = toX
            invalidate()
        }
    }


}