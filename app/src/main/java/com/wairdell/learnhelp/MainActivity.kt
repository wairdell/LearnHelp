package com.wairdell.learnhelp

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.DynamicDrawableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.wairdell.learnhelp.bessel.BesselDemoActivity
import com.wairdell.learnhelp.colormatrix.ColorMatrixActivity
import com.wairdell.learnhelp.compose.ComposeDemoActivity
import com.wairdell.learnhelp.coordinator.CoordinatorLayoutActivity
import com.wairdell.learnhelp.display.DisplayActivity
import com.wairdell.learnhelp.draghelper.ViewDragHelperActivity
import com.wairdell.learnhelp.interpolator.InterpolatorActivity
import com.wairdell.learnhelp.jetpack.paging.PagingSampleActivity
import com.wairdell.learnhelp.kodein.KodeinSampleActivity
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
        btn_coordinator_layout.setOnClickListener { startActivity(Intent(MainActivity@this, CoordinatorLayoutActivity::class.java)) }
        btn_recycler_view.setOnClickListener { startActivity(Intent(MainActivity@this, RecyclerViewActivity::class.java)) }
        btn_color_matrix.setOnClickListener { startActivity(Intent(MainActivity@this, ColorMatrixActivity::class.java)) }
        btn_kodein_sample.setOnClickListener { startActivity(Intent(MainActivity@this, KodeinSampleActivity::class.java)) }
        btn_jetpack_paging.setOnClickListener { startActivity(Intent(MainActivity@this, PagingSampleActivity::class.java)) }
        btn_compose.setOnClickListener { startActivity(Intent(MainActivity@this, ComposeDemoActivity::class.java)) }
        btn_display.setOnClickListener { startActivity(Intent(MainActivity@this, DisplayActivity::class.java)) }
        var tvImage = findViewById<TextView>(R.id.tv_image)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvImage.setOnClickListener {
                /*ValueAnimator.ofInt(0, 100).apply {
                    duration = 4000
                    addUpdateListener {
                        tvImage.text =  it.animatedValue.toString() + "%"
                        ProgressDrawableHelper.setProgress(tvImage, it.animatedValue as Int)
                    }
                    start()
                }*/
                ProgressDrawableHelper.setGlitter(tvImage);
            }

        };
    }

}
