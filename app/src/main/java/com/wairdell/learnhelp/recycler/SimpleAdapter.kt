package com.wairdell.learnhelp.recycler

import android.graphics.Color
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition
import java.util.*

open abstract class SimpleAdapter<T>(val layoutId: Int) : RecyclerView.Adapter<SimpleViewHolder>() {

    var data: MutableList<T>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SimpleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun getItem(position: Int): T? {
        return data?.get(position)
    }


}