package com.wairdell.learnhelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wairdell.learnhelp.bessel.BesselDemoActivity
import com.wairdell.learnhelp.interpolator.InterpolatorActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_bessel.setOnClickListener { startActivity(Intent(MainActivity@this, BesselDemoActivity::class.java)) }
        btn_interpolator.setOnClickListener { startActivity(Intent(MainActivity@this, InterpolatorActivity::class.java)) }
    }
}
