package com.wairdell.learnhelp.display

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }


}