package com.wairdell.learnhelp.widget

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 *    author : fengqiao
 *    date   : 2022/10/31 13:32
 *    desc   :
 */
class DripView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path: Path = Path()

    init {
        setBackgroundColor(Color.parseColor("#00ABFF"))

        paint.color = Color.TRANSPARENT
        paint.style = Paint.Style.FILL
//        setLayerType(LAYER_TYPE_SOFTWARE, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        drawInnerShadow(canvas, 10F, 20F, 30F, Color.argb((0.5F * 255).toInt(), 0, 0, 0))
//        drawInnerShadow(canvas, -10F, -10F, 15F, Color.argb((0.8F * 255).toInt(), 255, 255, 255))

        drawOuterShadow(canvas, 10F, 10F, 20F, Color.argb((0.3F * 255).toInt(), 0, 0, 0))
        drawOuterShadow(canvas, 15F, 15F, 30F, Color.argb((0.05F * 255).toInt(), 0, 0, 0))
        paint.setShadowLayer(30F, 15F, 15F, Color.argb((0.05F * 255).toInt(), 0, 0, 0))
        canvas.drawPath(path, paint)

        val saveLayer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)

        paint.color = Color.argb((0.8F * 255).toInt(), 255, 255, 255)
        paint.maskFilter = BlurMaskFilter(15F, BlurMaskFilter.Blur.OUTER)
        canvas.drawPath(path, paint)
        paint.maskFilter = null
        paint.color = Color.TRANSPARENT

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

        canvas.translate(-15F, -15F)
        canvas.drawPath(path, paint)
        canvas.translate(15F, 15F)

        paint.xfermode = null

        canvas.restoreToCount(saveLayer)




    }

    private fun drawOuterShadow(canvas: Canvas, dx: Float, dy: Float, radius: Float, color: Int) {
        canvas.translate(dx, dy)
        paint.color = color
        paint.maskFilter = BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER)
        canvas.drawPath(path, paint)

        paint.maskFilter = null
        canvas.translate(-dx, -dy)
    }

    private fun drawInnerShadow(canvas: Canvas, dx: Float, dy: Float, radius: Float, color: Int) {
        canvas.translate(dx, dy)
        paint.color = color
        paint.maskFilter = BlurMaskFilter(radius, BlurMaskFilter.Blur.INNER)
        canvas.drawPath(path, paint)

        paint.maskFilter = null
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val startPosition = Position(0.61F, 0.82F, 0.97F, 0.35F)
        val endPosition = Position(0.78F, 0.74F, 0.20F, 0.81F)
        val valueAnimator = ValueAnimator.ofObject(object : TypeEvaluator<Position> {

            val kMutablePropertyList = listOf(Position::north, Position::east, Position::south, Position::west)

            override fun evaluate(fraction: Float, startValue: Position, endValue: Position): Position {
                val result = Position()
                for (kMutableProperty1 in kMutablePropertyList) {
                    kMutableProperty1.set(result, (kMutableProperty1.get(endValue) - kMutableProperty1.get(startValue)) * fraction + kMutableProperty1.get(startValue))
                }
                return result
            }

        }, startPosition, endPosition)
        valueAnimator.duration = 3_000L
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.repeatMode = ValueAnimator.REVERSE
        valueAnimator.addUpdateListener {
            val position = it.animatedValue as Position
            createPath(position.north, position.east, position.south, position.west)
            invalidate()
        }
        valueAnimator.start()
    }


    private fun createPath(north: Float, east: Float, south: Float, west: Float) {
        val height = this.height * 1 / 3
        val width = this.width * 1 / 3
        path.reset()
//        tempPath.reset()
        val left = this.width / 6
        val top = this.height / 6
        path.moveTo(left + 0F, top + height * west)
        path.quadTo(left + 0F, top + 0F, left + width * north, top + 0F)
        path.quadTo(left + width.toFloat(), top + 0F, left + width.toFloat(), top + height * east)
        path.quadTo(left + width.toFloat(), top + height.toFloat(), left + width * south, top + height.toFloat())
        path.quadTo(left + 0F, top + height.toFloat(), left + 0F, top + height * west)
        path.close()
    }

    data class Position(var north: Float = 0F, var east: Float = 0F, var south: Float = 0F, var west: Float = 0F)
}