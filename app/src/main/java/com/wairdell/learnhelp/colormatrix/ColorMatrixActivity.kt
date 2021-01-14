package com.wairdell.learnhelp.colormatrix

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.wairdell.learnhelp.R
import kotlinx.android.synthetic.main.activity_color_matrix.*
import android.graphics.*
import android.view.Gravity
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.recycler.SimpleAdapter
import com.wairdell.learnhelp.recycler.SimpleViewHolder


class ColorMatrixActivity : AppCompatActivity() {

    private var srcBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_matrix)
        srcBitmap = BitmapFactory.decodeResource(resources, R.mipmap.test3)
        initMatrixLayout()
        initRecyclerView()
        btn_change.setOnClickListener {
            showMatrixImage()
        }
        btn_reset.setOnClickListener {
            initMatrixValue()
        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        var adapter = object : SimpleAdapter<MatrixWrapper>(R.layout.item_color_matrix) {

            override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
                getItem(position)?.run {
                    if (cacheBitmap == null) {
                        cacheBitmap = matrixImage(matrixColorProvider!!.generateMatrixColor(min + (max - min) * 0.5f))
                    }
                    holder.getView<ImageView>(R.id.iv_bg)?.setImageBitmap(cacheBitmap)
                    holder.getView<TextView>(R.id.tv_name)?.text = name
                    holder.itemView.setOnClickListener {
                        listenSeekBar(matrixColorProvider!!, min, max)
                    }
                }
            }
        }
        recycler_view.adapter = adapter
        var data: MutableList<MatrixWrapper> = mutableListOf()

        var matrix1 = MatrixWrapper()
        matrix1.matrixColorProvider = MatrixColorProvider.BlockWhiteColorProvider
        matrix1.name = "黑白"
        data.add(matrix1)

        //对比度(N取值为0到10)
        var matrix2 = MatrixWrapper()
        matrix2.matrixColorProvider = MatrixColorProvider.ContrastProvider
        matrix2.min = 0f
        matrix2.max = 10f
        matrix2.name = "对比度"
        data.add(matrix2)

        //亮度(N取值为-255到255)  注：一般取值为-100到100(这里是PS中的取值宽度)
        var matrix3 = MatrixWrapper()
        matrix3.matrixColorProvider = MatrixColorProvider.BrightnessProvider
        matrix3.min = -100f
        matrix3.max = 100f
        matrix3.name = "亮度"
        data.add(matrix3)

        var matrix4 = MatrixWrapper()
        matrix4.matrixColorProvider = MatrixColorProvider.ColorInversionProvider
        matrix4.name = "颜色反相"
        data.add(matrix4)

        //阈值(N取值为-255到255)
        var matrix5 = MatrixWrapper()
        matrix5.matrixColorProvider = MatrixColorProvider.ThresholdProvider
        matrix5.name = "阈值"
        matrix5.min = -255f
        matrix5.max = 255f
        data.add(matrix5)

        //色彩饱和度(N取值为0到255)  注：一般取值为0到100(这里是PS中的取值宽度)
        var matrix6 = MatrixWrapper()
        matrix6.matrixColorProvider = MatrixColorProvider.ColorSaturationProvider
        matrix6.name = "色彩饱和度"
        matrix6.min = 0f
        matrix6.max = 100f
        data.add(matrix6)

        adapter.data = data
    }

    private fun initMatrixLayout() {
        matrix_layout.post {
            var width = matrix_layout.width / 5
            var height = matrix_layout.height / 4
            for (i in 0..19) {
                var editText = EditText(this)
                editText.gravity = Gravity.CENTER
                editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                matrix_layout.addView(editText, width, height)
            }
            initMatrixValue()
        }
    }

    private fun listenSeekBar(matrixColorProvider: MatrixColorProvider, min: Float, max: Float) {
        onSeekBarChanged(matrixColorProvider, min, max, seek_bar.progress.toFloat() / seek_bar.max)
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                onSeekBarChanged(matrixColorProvider, min, max, progress.toFloat() / seekBar.max)
            }

        })
    }

    private fun onSeekBarChanged(matrixColorProvider: MatrixColorProvider, min: Float, max: Float, percent : Float) {
        var matrixColor = matrixColorProvider.generateMatrixColor(min + (max - min) * percent)
        for (i in 0..19) {
            (matrix_layout.getChildAt(i) as EditText).setText(matrixColor[i].toString())
        }
        iv_photo.setImageBitmap(matrixImage(matrixColor))
    }

    private fun initMatrixValue() {
        for (i in 0..19) {
            (matrix_layout.getChildAt(i) as TextView).text = if (i % 6 == 0) "1" else "0"
        }
    }

    private fun matrixImage(colorMatrixArray: FloatArray): Bitmap {
        var colorMatrix = ColorMatrix()
        colorMatrix.set(colorMatrixArray)

        val desBitmap = Bitmap.createBitmap(srcBitmap!!.getWidth(), srcBitmap!!.getHeight(), Bitmap.Config.ARGB_8888)
        var paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        var canvas = Canvas(desBitmap)
        canvas.drawBitmap(srcBitmap!!, 0f, 0f, paint)
        return desBitmap
    }

    private fun showMatrixImage() {
        var colorMatrixArray = FloatArray(20)
        for (i in 0..19) {
            var inputText = (matrix_layout.getChildAt(i) as TextView).text.toString()
            colorMatrixArray[i] = if (TextUtils.isEmpty(inputText)) 0f else inputText.toFloat()
        }

        iv_photo.setImageBitmap(matrixImage(colorMatrixArray))
    }

    class MatrixWrapper {

        var cacheBitmap: Bitmap? = null

        var matrixColorProvider: MatrixColorProvider? = null

        var name = ""

        var min = 0f
        var max = 0f

    }


}