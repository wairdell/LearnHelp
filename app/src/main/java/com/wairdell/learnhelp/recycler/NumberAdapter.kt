package com.wairdell.learnhelp.recycler

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import com.wairdell.learnhelp.R
import java.util.*

class NumberAdapter : SimpleAdapter<Int>(R.layout.item_recycler_view) {

    init {
        val random = Random()
        val list: MutableList<Int> = mutableListOf()
        for (i in 0..10) {
            list.add(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
        }
        data = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val viewHolder = super.onCreateViewHolder(parent, viewType)
        viewHolder.getView<TextView>(R.id.tv_number)?.setOnClickListener {
            notifyItemChanged(viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(getItem(position)!!)
        holder.getView<TextView>(R.id.tv_number)?.text = position.toString()
    }

}