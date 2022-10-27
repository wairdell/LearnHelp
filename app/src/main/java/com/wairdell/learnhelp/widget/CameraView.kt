package com.wairdell.learnhelp.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
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
    private val animator = AnimatorSet()

    var degree = 0F
        set(value) {
            field = value
            invalidate()
        }


    var rotateDegree = 10F
        set(value) {
            field = value
            invalidate()
        }

    init {
        val animator1 = ObjectAnimator.ofFloat(this, "degree", 0F, 70F).apply {
            duration = 1500
            interpolator = LinearInterpolator()
            //repeatCount = ValueAnimator.INFINITE
            //repeatMode = ValueAnimator.REVERSE
        }

        val animator2 = ObjectAnimator.ofFloat(this, "rotateDegree", 0F, 270F).apply {
            duration = 2500
            interpolator = LinearInterpolator()
            //repeatCount = ValueAnimator.INFINITE
            //repeatMode = ValueAnimator.REVERSE
        }

        animator.playSequentially(animator1, animator2)
        //animator.repeatCount = ValueAnimator.INFINITE

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.end()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = height / 2
        val fl = (width - bitmap.width) / 2F
        val ft = (height - bitmap.height) / 2F

        canvas.save()
        val py = bitmap.height / 2F
        val px = width / 2F
        canvas.rotate(-rotateDegree, px, py)
        canvas.clipRect(0, 0, width, centerY)
        canvas.rotate(rotateDegree, px, py)
        canvas.drawBitmap(bitmap, fl, ft, paint)
        canvas.restore()

        canvas.save()
        canvas.rotate(-rotateDegree, px, py)
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
        canvas.rotate(rotateDegree, px, py)
        canvas.drawBitmap(bitmap, fl, ft, paint)
        camera.restore()

        canvas.restore()

    }


}