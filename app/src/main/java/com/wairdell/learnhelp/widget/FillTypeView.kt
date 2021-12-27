package com.wairdell.learnhelp.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 *    author : fengqiao
 *    date   : 2021/12/16 18:03
 *    desc   :
 */
class FillTypeView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val path1: Path = Path()
    private val path2: Path = Path()
    private val paint: Paint = Paint()

    private val linePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePath: Path = Path()

    private val rectF = RectF()

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        path1.fillType = Path.FillType.EVEN_ODD
        path2.fillType = Path.FillType.WINDING

        linePaint.color = Color.BLACK
        linePaint.strokeWidth = 5F
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeJoin = Paint.Join.ROUND
        linePaint.textSize = 36F
        linePaint.pathEffect = CornerPathEffect(20F)
        linePaint.setShadowLayer(10F, 0F, 0F, Color.RED)

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val radius = (height / 4F).coerceAtMost(width / 8F)
        val firstX = ((width / 2F) - (radius * 3 / 2F)) / 2F
        val secondX = width / 4F - radius * 3 / 4F + width / 2F
        val firstY = height / 4F
        path1.reset()
        path2.reset()
        linePath.reset()

        /*linePath.moveTo(0F, height / 2F)
        linePath.lineTo(width / 2F, height / 2F)
        linePath.lineTo(width / 2F, height.toFloat())*/

        rectF.set(width / 4F, height / 4F, width / 4F + height / 2F, height / 4F + height / 2F)
        linePath.addArc(rectF,180F, 180f)


        path1.addCircle(firstX, firstY, radius, Path.Direction.CW)
        path1.addCircle(firstX + radius, firstY, radius, Path.Direction.CW)

        path1.addCircle(secondX, firstY, radius, Path.Direction.CW)
        path1.addCircle(secondX + radius, firstY, radius, Path.Direction.CCW)

        val secondY = height * 3 / 4F
        path2.addCircle(firstX, secondY, radius, Path.Direction.CW)
        path2.addCircle(firstX + radius, secondY, radius, Path.Direction.CW)

        path2.addCircle(secondX, secondY, radius, Path.Direction.CW)
        path2.addCircle(secondX + radius, secondY, radius, Path.Direction.CCW)


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path1, paint)
        canvas.drawPath(path2, paint)

        //canvas.drawPath(linePath, linePaint)
        //canvas.drawPath(linePath, linePaint)
        canvas.drawTextOnPath("Hello World", linePath, 0F, 0F, linePaint)
    }

}