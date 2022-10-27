package com.wairdell.learnhelp.bessel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.wairdell.learnhelp.R

class BesselDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bassel_demo)
        Log.e("TAG", intent.action ?: "")
        Log.e("TAG", intent.data.toString())
    }

}