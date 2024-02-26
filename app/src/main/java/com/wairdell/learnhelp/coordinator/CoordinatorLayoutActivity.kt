package com.wairdell.learnhelp.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.databinding.ActivityCoordinatorLayoutBinding
import com.wairdell.learnhelp.recycler.NumberAdapter

class CoordinatorLayoutActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        binding.recyclerView.adapter = NumberAdapter()
    }

}