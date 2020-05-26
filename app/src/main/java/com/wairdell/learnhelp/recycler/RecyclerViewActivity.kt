package com.wairdell.learnhelp.recycler

import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.recycler.slide.ItemTouchHelperCallback
import com.wairdell.learnhelp.recycler.slide.SlideLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        var adapter = NumberAdapter()
        recycler_view1.layoutManager = SimpleHorizontalLayoutManager()
        recycler_view1.adapter = adapter
        /*var snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler_view1)*/

        var adapter2 = NumberAdapter()
        var itemTouchHelperCallback = ItemTouchHelperCallback(adapter2)
        var itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        recycler_view2.layoutManager = SlideLayoutManager(recycler_view2, itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recycler_view2)
        recycler_view2.adapter = adapter2
    }


}