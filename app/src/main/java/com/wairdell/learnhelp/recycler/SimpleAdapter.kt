package com.wairdell.learnhelp.recycler

import android.graphics.Color
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition
import java.util.*

open abstract class SimpleAdapter<T>(val layoutId: Int) : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {

    var data: MutableList<T>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAdapter.SimpleViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SimpleViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    fun getItem(position: Int): T? {
        return data?.get(position)
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cache = SparseArray<View>()

        fun <V : View> getView(id: Int): V? {
            var view: View? = null
            view = cache.get(id)
            if (view == null) {
                view = itemView.findViewById<View>(id)
                cache.put(id, view)
            }
            return view as? V
        }

    }

}