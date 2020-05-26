package com.wairdell.learnhelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.bessel.BesselDemoActivity
import com.wairdell.learnhelp.draghelper.ViewDragHelperActivity
import com.wairdell.learnhelp.interpolator.InterpolatorActivity
import com.wairdell.learnhelp.recycler.RecyclerViewActivity
import com.wairdell.learnhelp.xfermode.XfermodeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_bessel.setOnClickListener { startActivity(Intent(MainActivity@this, BesselDemoActivity::class.java)) }
        btn_interpolator.setOnClickListener { startActivity(Intent(MainActivity@this, InterpolatorActivity::class.java)) }
        btn_xfermode.setOnClickListener { startActivity(Intent(MainActivity@this, XfermodeActivity::class.java)) }
        btn_drag_helper.setOnClickListener { startActivity(Intent(MainActivity@this, ViewDragHelperActivity::class.java)) }
        btn_recycler_view.setOnClickListener { startActivity(Intent(MainActivity@this, RecyclerViewActivity::class.java)) }
    }
}
