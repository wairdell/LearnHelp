package com.wairdell.learnhelp.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.recycler.NumberAdapter
import kotlinx.android.synthetic.main.activity_coordinator_layout.*

class CoordinatorLayoutActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        setSupportActionBar(tool_bar)
        recycler_view.adapter = NumberAdapter()
    }

}