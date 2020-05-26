package com.wairdell.learnhelp

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class Utils {

    companion object {

        fun getScreenWidth(context: Context): Int {
            var displayMetrics = DisplayMetrics()
            var wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            wm?.defaultDisplay?.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

    }
}