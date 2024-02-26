package com.wairdell.learnhelp.xfermode

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.databinding.ActivityXfermodeBinding

class XfermodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityXfermodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.adapter = adapter
    }

}