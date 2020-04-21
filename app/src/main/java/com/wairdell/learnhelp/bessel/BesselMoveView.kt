package com.wairdell.learnhelp.bessel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BesselMoveView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var fixedPaint: Paint = Paint()

    var besselPaint: Paint = Paint()

    var controlX = 0f

    var controlY = 0f

    var besselPath: Path = Path()

    var startX = 0f
    var startY = 0f
    var endX = 0f
    var endY = 0f

    constructor(context: Context?) : this(context, null)

    init {
        fixedPaint.color = Color.parseColor("#999999")
        fixedPaint.strokeWidth = 2f
        fixedPaint.isAntiAlias = true

        besselPaint.color = Color.parseColor("#FF0000")
        besselPaint.strokeWidth = 2f
        besselPaint.isAntiAlias = true
        besselPaint.style = Paint.Style.STROKE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        startX = 0f
        startY = height.toFloat() / 2
        endX = width.toFloat()
        endY = height.toFloat() / 2
        controlX = width.toFloat() / 2
        controlY = 0f
        besselPath.reset()
        besselPath.moveTo(startX, startY)
        besselPath.quadTo(controlX, controlY, endX, endY)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(startX, startY, controlX, controlY, fixedPaint)
        canvas?.drawLine(controlX, controlY, endX, endY, fixedPaint)
        /*for (i in 0..100) {
            var tempStartX = startX + (controlX - startX) * i / 100
            var tempStartY = startY + (controlY - startY) * i / 100
            var tempEndX = controlX + (endX - controlX) * i / 100
            var tempEndY = controlY + (endY - controlY) * i / 100
            canvas?.drawPoint(
                tempStartX + (tempEndX - tempStartX) * i / 100,
                tempStartY + (tempEndY - tempStartY) * i / 100,
                besselPaint
            )

        }*/
        canvas?.drawPath(besselPath, besselPaint)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        controlX = event?.x ?: 0f
        controlY = event?.y ?: 0f
        besselPath.reset()
        besselPath.moveTo(startX, startY)
        besselPath.quadTo(controlX, controlY, endX, endY)
        invalidate()
        if (event?.action == MotionEvent.ACTION_DOWN) {
            return true
        } else {
            return super.onTouchEvent(event)
        }
    }

}