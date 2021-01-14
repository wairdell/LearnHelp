package com.wairdell.learnhelp.jetpack.paging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.common.ViewModelFactory
import kotlinx.android.synthetic.main.activity_jetpack_paging.*

class PagingSampleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_paging)
        val adapter = TestAdapter()
        
        recycler_view.adapter = adapter
        val viewModel = ViewModelFactory.create(TestViewModel::class.java)
        viewModel.testList.observe(this, Observer {
            refresh_layout.isRefreshing = false
            adapter.submitList(it)
        })
        refresh_layout.setOnRefreshListener {
            viewModel.invalidateDataSource()
        }
    }

}