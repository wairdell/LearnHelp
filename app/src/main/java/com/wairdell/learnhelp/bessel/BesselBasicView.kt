package com.wairdell.learnhelp.bessel

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BesselBasicView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var fixedPaint : Paint = Paint()

    var besselPaint : Paint = Paint()

    constructor(context: Context?) : this(context, null)

    init {
        fixedPaint.color = Color.parseColor("#999999")
        fixedPaint.strokeWidth = 2f
        fixedPaint.isAntiAlias = true

        besselPaint.color = Color.parseColor("#FF0000")
        besselPaint.style = Paint.Style.STROKE
        besselPaint.strokeWidth = 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var startX = 0f
        var startY = height.toFloat() / 2
        var controlX = width.toFloat() / 2
        var controlY = 0f
        var endX = width.toFloat()
        var endY = height.toFloat() / 2
        canvas?.drawLine(startX, startY, controlX, controlY, fixedPaint)
        canvas?.drawLine(controlX, controlY, endX, endY, fixedPaint)
        for(i in 0..100) {
            var tempStartX = startX + (controlX - startX) * i / 100
            var tempStartY = startY + (controlY - startY) * i / 100
            var tempEndX = controlX + (endX - controlX) * i / 100
            var tempEndY = controlY + (endY - controlY) * i / 100
            canvas?.drawPoint(tempStartX +  (tempEndX - tempStartX) * i / 100, tempStartY + (tempEndY - tempStartY) * i / 100, besselPaint)

        }
    }
}