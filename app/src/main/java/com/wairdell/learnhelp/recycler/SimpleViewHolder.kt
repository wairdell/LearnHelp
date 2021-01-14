package com.wairdell.learnhelp.recycler

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

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