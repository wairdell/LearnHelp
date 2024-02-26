package com.wairdell.learnhelp.colormatrix

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wairdell.learnhelp.R
import com.wairdell.learnhelp.databinding.ActivityColorMatrixBinding
import com.wairdell.learnhelp.recycler.SimpleAdapter
import com.wairdell.learnhelp.recycler.SimpleViewHolder


class ColorMatrixActivity : AppCompatActivity() {

    private lateinit var srcBitmap: Bitmap
    private lateinit var binding: ActivityColorMatrixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColorMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)
        srcBitmap = BitmapFactory.decodeResource(resources, R.mipmap.test3)
        initMatrixLayout()
        initRecyclerView()
        binding.btnChange.setOnClickListener {
            showMatrixImage()
        }
        binding.btnReset.setOnClickListener {
            initMatrixValue()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val adapter = object : SimpleAdapter<MatrixWrapper>(R.layout.item_color_matrix) {

            override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
                getItem(position)?.run {
                    cacheBitmap = cacheBitmap ?: matrixImage(matrixColorProvider.generateMatrixColor(min + (max - min) * 0.5f))
                    holder.getView<ImageView>(R.id.iv_bg)?.setImageBitmap(cacheBitmap)
                    holder.getView<TextView>(R.id.tv_name)?.text = name
                    holder.itemView.setOnClickListener {
                        listenSeekBar(matrixColorProvider, min, max)
                    }
                }
            }
        }
        binding.recyclerView.adapter = adapter
        val data: MutableList<MatrixWrapper> = mutableListOf()

        val matrix1 = MatrixWrapper(MatrixColorProvider.BlockWhiteColorProvider, "黑白")
        data.add(matrix1)

        //对比度(N取值为0到10)
        val matrix2 = MatrixWrapper(MatrixColorProvider.ContrastProvider, "对比度")
        matrix2.min = 0f
        matrix2.max = 10f
        data.add(matrix2)

        //亮度(N取值为-255到255)  注：一般取值为-100到100(这里是PS中的取值宽度)
        val matrix3 = MatrixWrapper(MatrixColorProvider.BrightnessProvider, "亮度")
        matrix3.min = -100f
        matrix3.max = 100f
        data.add(matrix3)

        val matrix4 = MatrixWrapper(MatrixColorProvider.ColorInversionProvider, "颜色反相")
        data.add(matrix4)

        //阈值(N取值为-255到255)
        val matrix5 = MatrixWrapper(MatrixColorProvider.ThresholdProvider, "阈值")
        matrix5.min = -255f
        matrix5.max = 255f
        data.add(matrix5)

        //色彩饱和度(N取值为0到255)  注：一般取值为0到100(这里是PS中的取值宽度)
        val matrix6 = MatrixWrapper(MatrixColorProvider.ColorSaturationProvider, "色彩饱和度")
        matrix6.min = 0f
        matrix6.max = 100f
        data.add(matrix6)

        adapter.data = data
    }

    private fun initMatrixLayout() {
        binding.matrixLayout.post {
            val width = binding.matrixLayout.width / 5
            val height = binding.matrixLayout.height / 4
            for (i in 0..19) {
                val editText = EditText(this)
                editText.gravity = Gravity.CENTER
                editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                binding.matrixLayout.addView(editText, width, height)
            }
            initMatrixValue()
        }
    }

    private fun listenSeekBar(matrixColorProvider: MatrixColorProvider, min: Float, max: Float) {
        onSeekBarChanged(matrixColorProvider, min, max, binding.seekBar.progress.toFloat() / binding.seekBar.max)
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
        val matrixColor = matrixColorProvider.generateMatrixColor(min + (max - min) * percent)
        for (i in 0..19) {
            (binding.matrixLayout.getChildAt(i) as EditText).setText(matrixColor[i].toString())
        }
        binding.ivPhoto.setImageBitmap(matrixImage(matrixColor))
    }

    private fun initMatrixValue() {
        for (i in 0..19) {
            (binding.matrixLayout.getChildAt(i) as TextView).text = if (i % 6 == 0) "1" else "0"
        }
    }

    private fun matrixImage(colorMatrixArray: FloatArray): Bitmap {
        val colorMatrix = ColorMatrix()
        colorMatrix.set(colorMatrixArray)

        val desBitmap = Bitmap.createBitmap(srcBitmap.width, srcBitmap.height, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        val canvas = Canvas(desBitmap)
        canvas.drawBitmap(srcBitmap, 0f, 0f, paint)
        return desBitmap
    }

    private fun showMatrixImage() {
        val colorMatrixArray = FloatArray(20)
        for (i in 0..19) {
            val inputText = (binding.matrixLayout.getChildAt(i) as TextView).text.toString()
            colorMatrixArray[i] = if (TextUtils.isEmpty(inputText)) 0f else inputText.toFloat()
        }

        binding.ivPhoto.setImageBitmap(matrixImage(colorMatrixArray))
    }

    data class MatrixWrapper(var matrixColorProvider: MatrixColorProvider, var name: String) {
        var cacheBitmap: Bitmap? = null
        var min = 0f
        var max = 0f
    }


}