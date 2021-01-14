package com.wairdell.learnhelp

import android.content.Context
import android.view.View

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