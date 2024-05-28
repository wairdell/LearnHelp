package com.wairdell.learnhelp

import android.content.Context
import android.graphics.Color
import android.view.View
import java.util.Random

fun Context.px2dp(pxValue: Float): Float {
    val scale: Float = resources.displayMetrics.density;
    return pxValue / scale + 0.5f;
}

fun Context.px2dp(pxValue: Int): Float {
    return px2dp(pxValue.toFloat())
}

fun View.px2dp(pxValue: Float) : Float {
    return context.px2dp(pxValue)
}

fun View.px2dp(pxValue: Int) : Float {
    return context.px2dp(pxValue)
}

val random = Random()
fun randomColor(): Int {
    return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))
}