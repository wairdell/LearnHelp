package com.wairdell.learnhelp.widget

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.PathInterpolator

/**
 *    author : fengqiao
 *    date   : 2021/12/20 9:51
 *    desc   :
 */
class AnimatorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var progress: Float = 0F
        set(value) {
            field = value
            invalidate()
        }
    private val animator: ObjectAnimator
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val ovalRect: RectF = RectF()
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textBounds = Rect()

    init {
        /*val keyframe1 = Keyframe.ofFloat(0F, 0F)
        val keyframe2 = Keyframe.ofFloat(0.5F, 80F)
        val keyframe3 = Keyframe.ofFloat(1F, 60F)
        val holder =
            PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3)
        animator = ObjectAnimator.ofPropertyValuesHolder(this, holder)*/
        val path = Path()
        path.lineTo(0F, 0F)
        path.lineTo(0.8F, 1.2F)
        path.lineTo(1.0F, 1.0F)
        val pathInterpolator = PathInterpolator(path)
        animator = ObjectAnimator.ofFloat(this, "progress", 0F, 80F)
        animator.interpolator = pathInterpolator
        animator.duration = 2000L
        setOnClickListener {
            animator.start()
        }

        circlePaint.strokeWidth = 20F
        circlePaint.strokeCap = Paint.Cap.ROUND
        circlePaint.color = Color.RED
        circlePaint.style = Paint.Style.STROKE

        textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18F, resources.displayMetrics)
        textPaint.color = Color.WHITE
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val padding = circlePaint.strokeWidth / 2
        ovalRect.set(padding, padding, width - padding, height - padding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(ovalRect, -90F, 360 * progress / 100, false, circlePaint)

        val text = "${progress.toInt()}%"
        //val measureWidth = textPaint.measureText("text")
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        canvas.drawText(text, (width - (textBounds.right - textBounds.left)) / 2F, (height + (textBounds.bottom - textBounds.top)) / 2F, textPaint)
    }

}