package com.wairdell.learnhelp.display

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wairdell.learnhelp.ProgressDrawableHelper
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.widget.FlowLetterView

/**
 *    author : fengqiao
 *    date   : 2021/12/20 18:13
 *    desc   :
 */
class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val letterView = findViewById<FlowLetterView>(R.id.flow_letter_view)
        letterView.setOnClickListener {
            letterView.drawLetter()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ProgressDrawableHelper.setGlitter(findViewById<TextView>(R.id.tv_image))
        }
    }


}