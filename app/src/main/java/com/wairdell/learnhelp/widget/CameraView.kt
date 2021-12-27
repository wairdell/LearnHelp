package com.wairdell.learnhelp.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.wairdell.learnhelp.R

/**
 *    author : fengqiao
 *    date   : 2021/12/17 16:04
 *    desc   :
 */
class CameraView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    private val paint = Paint()
    private val camera = Camera()
    private val animator = ObjectAnimator.ofFloat(this, "degree", 0F, 180F)

    var degree = 0F
        set(value) {
            field = value
            invalidate()
        }

    init {
        animator.duration = 2500
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onAnimationEnd() {
        super.onAnimationEnd()
        animator.end()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = height / 2
        val fl = (width - bitmap.width) / 2F

        canvas.save()
        canvas.clipRect(0, 0, width, centerY)
        canvas.drawBitmap(bitmap, fl, (height - bitmap.height) / 2F, paint)
        canvas.restore()

        canvas.save()
        if (degree < 90) {
            canvas.clipRect(0, centerY, width, height)
        } else {
            canvas.clipRect(0, 0, width, centerY)
        }
        camera.save()
        camera.rotateX(degree)
        canvas.translate(width / 2F, height / 2F)
        camera.applyToCanvas(canvas)
        canvas.translate(width / -2F, height / -2F)
        canvas.drawBitmap(bitmap, fl, (height - bitmap.height) / 2F, paint)
        camera.restore()

        canvas.restore()
    }


}