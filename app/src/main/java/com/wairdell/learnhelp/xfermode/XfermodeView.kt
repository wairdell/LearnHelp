package com.wairdell.learnhelp.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wairdell.learnhelp.R

class XfermodeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var maskBitmap: Bitmap? = null
    var maskCanvas: Canvas? = null
    var defaultMaskColor = Color.LTGRAY
    var touchPath: Path = Path()
    var paint: Paint = Paint()

    lateinit var imageBitmap: Bitmap
    lateinit var imageCanvas: Canvas

    constructor(context: Context?) : this(context, null)

    init {
        paint.apply {
            alpha = 0
            xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeWidth = 50f
            strokeCap = Paint.Cap.ROUND
        }
        imageBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_test).copy(Bitmap.Config.ARGB_8888, true)
        imageCanvas = Canvas(imageBitmap)
        val imagePaint = Paint().apply {
            /*shader = LinearGradient(
                0f,
                0f,
                imageBitmap!!.width.toFloat(),
                imageBitmap!!.height.toFloat(),
                Color.parseColor("#FF000000"),
                Color.parseColor("#00000000"),
                Shader.TileMode.MIRROR
            )*/
            color = Color.RED
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
        val rectF: RectF = RectF(0f, 0f, imageBitmap.width.toFloat(), imageBitmap.height.toFloat())
        imageCanvas.drawArc(rectF, 0f, 90f, true, imagePaint)
        //imageCanvas?.drawRect(0f, 0f, imageBitmap!!.width.toFloat(), imageBitmap!!.height.toFloat(), imagePaint)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(imageBitmap, 0f, 0f, null)
        maskBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (changed || maskBitmap == null) {
            maskBitmap = Bitmap.createBitmap(right - left, bottom - top, Bitmap.Config.ARGB_8888)
            maskCanvas = Canvas(maskBitmap!!)
            maskCanvas?.drawColor(defaultMaskColor)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        if (event?.action == MotionEvent.ACTION_DOWN) {
            touchPath.reset()
            touchPath.moveTo(event.x, event.y)
            return true
        } else if (event?.action == MotionEvent.ACTION_MOVE) {
            touchPath.lineTo(event.x, event.y)
        }
        maskCanvas?.drawPath(touchPath, paint)
        invalidate()
        return true
    }

}