package com.wairdell.learnhelp.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

/**
 *    author : fengqiao
 *    date   : 2022/11/2 15:20
 *    desc   :
 */
class LetterGradient @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bl = 20
    private var startRates = floatArrayOf()
    private var rates = floatArrayOf()
    private var endRates = floatArrayOf()
    val gradientColors = intArrayOf(Color.parseColor("#000000"),
        Color.parseColor("#000000"),
        Color.parseColor("#0EE30E"),
        Color.parseColor("#000000"),
        Color.parseColor("#000000"))
    val gradientPositions = floatArrayOf(0F, 0.2F, 0.2F, 1F, 1F)
    var charArray = arrayOf<CharArray>()

    init {
        paint.textSize = 14F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startRates = FloatArray(w / 8) {
            return@FloatArray Float.NaN
        }
        rates = startRates.clone()
        endRates = startRates.clone()
        charArray = Array<CharArray>(w / bl) {
            return@Array charArrayOf()
        }
        for (i in 0 until w / bl) {
            val innerArray = CharArray(height / bl)
            for (j in 0 until  h / bl) {
                innerArray[j] = ('a' + Random.nextInt('z' - 'a'))
            }
            charArray[i] = innerArray
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until width step bl) {
            val p = i / bl
            if (p >= charArray.size) {
                continue
            }
            val s1 = 0.2F * (Random.nextInt(100) / 100F)
            val s2 = 0.8F * (Random.nextInt(100) / 100F)
            val step = 0.02F * (Random.nextInt(100) / 100F)
            (if (startRates[p].isNaN()) -s2 else startRates[p]).let { gradientPositions[1] = it.coerceAtLeast(0F); startRates[p] = it }
            (if(rates[p].isNaN()) -s1 else rates[p]).let { gradientPositions[2] = it.coerceAtLeast(0F); rates[p] = it }
            (if(endRates[p].isNaN()) step else endRates[p]).let { gradientPositions[3] = it; endRates[p] = it }

            paint.shader = LinearGradient(0F, 0F, 0F, height.toFloat(), gradientColors, gradientPositions, Shader.TileMode.CLAMP)
            Log.e("TAG", "p = ${p} ${gradientPositions.contentToString()} [${startRates[p]},${rates[p]},${endRates[p]}]")
            for (j in 0 until height step bl) {
                if (j / bl >= charArray[p].size) {
                    continue
                }
                canvas.drawText(charArray[p][j / bl].toString(), i.toFloat(), j.toFloat(), paint)
            }
            startRates[p] += step
            rates[p] += step
            endRates[p] += step
            if (startRates[p] > 1) {
                if (startRates[p] > rates[p]) {
                    startRates[p] = rates[p]
                } else {
                    startRates[p] = -s2
                }
            }
            if (rates[p] > 1) {
                if (startRates[p] == -s2) {
                    rates[p] = -s1
                } else {
                    rates[p] = 1F
                }
            }
            if (endRates[p] > 1) {
                if (rates[p] == -s1 && startRates[p] == -s2) {
                    endRates[p] = step
                } else {
                    endRates[p] = 1F
                }
            }
        }
        postInvalidate()
    }

}