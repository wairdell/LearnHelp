package com.wairdell.learnhelp.widget

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.random.Random

/**
 *    author : fengqiao
 *    date   : 2022/10/27 13:32
 *    desc   :
 */
class FlowLetterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var bitmapCanvas: Canvas? = null
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var columnTopArray = IntArray(0)
    private var columnSize = 0
    private var rowSize = 0
    private val textBounds = Rect()
    private var padding = 0
    private var drawRunnable: Runnable? = null

    init {
        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18F, resources.displayMetrics)
        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 2F, resources.displayMetrics).toInt()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888).let {
            bitmap = it
            bitmapCanvas = Canvas(it)
        }
        paint.getTextBounds("w", 0, 1, textBounds)
        paint.typeface = Typeface.MONOSPACE
        rowSize = textBounds.right - textBounds.left + padding * 2
        paint.letterSpacing = 0F
        columnSize = textBounds.bottom - textBounds.top
        columnTopArray = IntArray(w / rowSize)
    }

    private fun randomLetter() = 'a' + Random.nextInt('z' - 'a')

    private fun randomColor(): Int = Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))

    public fun drawLetter() {
        bitmapCanvas?.let {
            paint.color = Color.parseColor("#40FFFFFF")
            it.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
            for (i in columnTopArray.indices) {
                paint.color = randomColor()
                val column = columnTopArray[i] + 1
                val x = (i * rowSize).toFloat() + padding
                val y = (column * columnSize).toFloat()
                it.drawText(randomLetter().toString(), x, y, paint)
                if (y >= height && Random.nextInt(100) < 3) {
                    columnTopArray[i] = 0
                } else {
                    columnTopArray[i] = column + 1
                }
            }
            invalidate()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        bitmap?.let {
            canvas.drawBitmap(it, 0F, 0F, null)
        }

    }

    private fun startDrawLetterAnimation() {
        drawLetter()
        Runnable {
            if (isAttachedToWindow) startDrawLetterAnimation()
        }.let {
            drawRunnable = it
            handler.postDelayed(it, 30)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startDrawLetterAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        drawRunnable?.let {
            handler.removeCallbacks(it)
            drawRunnable = null
        }
    }

}