package com.wairdell.learnhelp.interpolator

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.R
import kotlinx.android.synthetic.main.activity_interpolator.*

class InterpolatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interpolator)
        var list: List<Interpolator> = listOf(
            AccelerateDecelerateInterpolator(),
            AccelerateInterpolator(),
            AnticipateInterpolator(),
            AnticipateOvershootInterpolator(),
            DecelerateInterpolator(),
            CycleInterpolator(1f),
            LinearInterpolator(),
            OvershootInterpolator()
        )
        var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> =
            object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    var view: InterpolatorExampleView = InterpolatorExampleView(this@InterpolatorActivity)
                    view.setBackgroundColor(Color.parseColor("#EEEEEE"))
                    var layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300)
                    layoutParams.leftMargin = 10
                    layoutParams.topMargin = 10
                    view.layoutParams = layoutParams
                    var viewHolder: RecyclerView.ViewHolder = object : RecyclerView.ViewHolder(view) {}
                    return viewHolder;
                }

                override fun getItemCount(): Int {
                    return list.size
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    (holder.itemView as InterpolatorExampleView?)?.interpolator = list[position]
                }

            }
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.adapter = adapter
        btn_start.setOnClickListener {
            for (i in 0..recycler_view.childCount) {
                (recycler_view.getChildAt(i) as InterpolatorExampleView?)?.startAni()
            }
        }
    }
}