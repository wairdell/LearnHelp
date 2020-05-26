package com.wairdell.learnhelp.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wairdell.learnhelp.R
import android.graphics.Canvas.ALL_SAVE_FLAG
import android.os.Build


class HiveView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mPath: Path = Path()

    var mPaint: Paint = Paint()

    var xfermode: Xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    var bitmap: Bitmap? = null

    var srcRect = Rect()
    var dstRectF = RectF()

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_test)
        mPaint.style = Paint.Style.FILL
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerId : Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        else
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        //canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        bitmap?.let {
            srcRect.set(0, 0, it.width, it.height)
            dstRectF.set(0f, 0f, width.toFloat(), height.toFloat())
            canvas.drawBitmap(it, srcRect, dstRectF, mPaint)
        }
        mPaint.xfermode = xfermode
        canvas.drawPath(mPath, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            initPath()
        }
    }

    private fun initPath() {
        val l = width / 2f
        val h = Math.sqrt(3.0).toFloat() * l
        val top = (height - h) / 2
        mPath.reset()
        mPath.moveTo(l / 2, top)
        mPath.lineTo(0f, h / 2 + top)
        mPath.lineTo(l / 2, h + top)
        mPath.lineTo((l * 1.5).toFloat(), h + top)
        mPath.lineTo(2 * l, h / 2 + top)
        mPath.lineTo((l * 1.5).toFloat(), top)
        mPath.lineTo(l / 2, top)
        mPath.close()
    }

}