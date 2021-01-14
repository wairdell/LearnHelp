package com.wairdell.learnhelp.jetpack.paging

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.recycler.SimpleViewHolder

class TestAdapter: PagedListAdapter<TestData, SimpleViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<TestData>() {

            override fun areItemsTheSame(oldItem: TestData, newItem: TestData): Boolean {
                return oldItem.contet == oldItem.contet
            }

            override fun areContentsTheSame(oldItem: TestData, newItem: TestData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_paging_test, parent, false)
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.getView<TextView>(R.id.tv_content)?.text = getItem(position)?.contet
    }




}