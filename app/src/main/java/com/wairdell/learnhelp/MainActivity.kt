package com.wairdell.learnhelp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wairdell.learnhelp.bessel.BesselDemoActivity
import com.wairdell.learnhelp.colormatrix.ColorMatrixActivity
import com.wairdell.learnhelp.compose.ComposeDemoActivity
import com.wairdell.learnhelp.coordinator.CoordinatorLayoutActivity
import com.wairdell.learnhelp.databinding.ActivityMainBinding
import com.wairdell.learnhelp.display.DisplayActivity
import com.wairdell.learnhelp.draghelper.ViewDragHelperActivity
import com.wairdell.learnhelp.fragment.FragmentMockActivity
import com.wairdell.learnhelp.interpolator.InterpolatorActivity
import com.wairdell.learnhelp.jetpack.paging.PagingSampleActivity
import com.wairdell.learnhelp.kodein.KodeinSampleActivity
import com.wairdell.learnhelp.recycler.RecyclerViewActivity
import com.wairdell.learnhelp.xfermode.XfermodeActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        binding.btnBessel.setOnClickListener { startActivity(Intent(this, BesselDemoActivity::class.java)) }
        binding.btnInterpolator.setOnClickListener { startActivity(Intent(this, InterpolatorActivity::class.java)) }
        binding.btnXfermode.setOnClickListener { startActivity(Intent(this, XfermodeActivity::class.java)) }
        binding.btnDragHelper.setOnClickListener { startActivity(Intent(this, ViewDragHelperActivity::class.java)) }
        binding.btnCoordinatorLayout.setOnClickListener { startActivity(Intent(this, CoordinatorLayoutActivity::class.java)) }
        binding.btnRecyclerView.setOnClickListener { startActivity(Intent(this, RecyclerViewActivity::class.java)) }
        binding.btnColorMatrix.setOnClickListener { startActivity(Intent(this, ColorMatrixActivity::class.java)) }
        binding.btnKodeinSample.setOnClickListener { startActivity(Intent(this, KodeinSampleActivity::class.java)) }
        binding.btnJetpackPaging.setOnClickListener { startActivity(Intent(this, PagingSampleActivity::class.java)) }
        binding.btnCompose.setOnClickListener { startActivity(Intent(this, ComposeDemoActivity::class.java)) }
        binding.btnDisplay.setOnClickListener {
            startActivity(Intent(this, DisplayActivity::class.java))
        }
        binding.btnFragment.setOnClickListener {
            startActivity(Intent(this, FragmentMockActivity::class.java))
        }

    }


}
