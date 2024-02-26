package com.wairdell.learnhelp.jetpack.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.common.ViewModelFactory
import com.wairdell.learnhelp.databinding.ActivityJetpackPagingBinding

class PagingSampleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJetpackPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = TestAdapter()

        binding.recyclerView.adapter = adapter
        val viewModel = ViewModelFactory.create(TestViewModel::class.java)
        viewModel.testList.observe(this, Observer {
            binding.refreshLayout.isRefreshing = false
            adapter.submitList(it)
        })
        binding.refreshLayout.setOnRefreshListener {
            viewModel.invalidateDataSource()
        }
    }

}