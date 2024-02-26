package com.wairdell.learnhelp.recycler

import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.databinding.ActivityRecyclerViewBinding
import com.wairdell.learnhelp.recycler.slide.ItemTouchHelperCallback
import com.wairdell.learnhelp.recycler.slide.SlideLayoutManager

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var adapter = NumberAdapter()
        binding.recyclerView1.layoutManager = SimpleHorizontalLayoutManager()
        binding.recyclerView1.adapter = adapter
        /*var snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler_view1)*/

        var adapter2 = NumberAdapter()
        var itemTouchHelperCallback = ItemTouchHelperCallback(adapter2)
        var itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        binding.recyclerView2.layoutManager = SlideLayoutManager(binding.recyclerView2, itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView2)
        binding.recyclerView2.adapter = adapter2
    }


}