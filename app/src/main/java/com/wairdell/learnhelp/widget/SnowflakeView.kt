package com.wairdell.learnhelp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random
import kotlin.random.nextInt

/**
 *    author : fengqiao
 *    date   : 2022/10/31 10:31
 *    desc   :
 */
class SnowflakeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val snowflakeArray: Array<Snowflake> = Array(500) {
        Snowflake()
    }
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        setBackgroundColor(Color.BLACK)
        paint.color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        for (i in snowflakeArray.indices) {
            snowflakeArray[i].x = Random.nextInt(w).toFloat()
            snowflakeArray[i].y = Random.nextInt(h).toFloat()
            snowflakeArray[i].speed = 1F + Random.nextInt(3).toFloat()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startSnowflakeAnimation()
    }

    private fun startSnowflakeAnimation() {
        snowflakeArray.forEach {
            it.y += it.speed
            if (it.y > height) {
                it.y = 0F
            }
        }
        if (!isAttachedToWindow) {
            return
        }
        invalidate()
        postOnAnimation(this::startSnowflakeAnimation)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        snowflakeArray.forEach {
            canvas.drawCircle(it.x, it.y, 3F, paint)
        }
    }

    data class Snowflake(var x: Float = 0F, var y: Float = 0f, var speed: Float = 0F)

}