package com.wairdell.learnhelp.xfermode

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.R
import kotlinx.android.synthetic.main.activity_xfermode.*

class XfermodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xfermode)
        var adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(XfermodeDisplayView(parent.context)) {}
            }

            override fun getItemCount(): Int {
                return PorterDuff.Mode.values().size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as XfermodeDisplayView).mode = PorterDuff.Mode.values()[position]
            }
        }
        recycler_view.layoutManager = GridLayoutManager(this, 4)
        recycler_view.adapter = adapter
    }

}