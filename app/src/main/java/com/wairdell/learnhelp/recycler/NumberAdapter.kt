package com.wairdell.learnhelp.recycler

import android.graphics.Color
import android.widget.TextView
import com.wairdell.learnhelp.R
import java.util.*

class NumberAdapter : SimpleAdapter<Int>(R.layout.item_recycler_view) {

    init {
        var list: MutableList<Int> = mutableListOf()
        for (i in 0..10) {
            list.add(i)
        }
        data = list
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        var random = Random()
        var bg = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255))
        holder.itemView.setBackgroundColor(bg)
        holder.getView<TextView>(R.id.tv_number)?.text = getItem(position).toString()
    }

}