package com.wairdell.learnhelp.xfermode

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.wairdell.learnhelp.R


class XfremodeBitmapWave(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val mMode3 = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)//透明出来
    //private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.XOR);//红色覆盖
    private val mMode2 = PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)//浸泡感觉
    private val mMode1 = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)//重叠部分消失 可以表示反向加载
    private val mMode0 = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)//基本波浪


    // private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);//
    // private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);//
    // private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);//
    private var mWavePaint: Paint? = null
    private var bitmapPaint: Paint? = null
    private var mCanvas: Canvas? = null
    private var mBitmap: Bitmap? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mPath: Path? = null
    private var mOffset: Int = 0
    var animator: ValueAnimator? = null

    private var mCenterY: Float = 0f
    private var mWaveCount: Int = 0
    //    背景图
    var bitmap: Bitmap? = null
    //浪宽
    private var mWaveLength = 700
    //波浪比例(高度)
    private var progerss = 50
    //波浪振幅高
    private val mWaveHeight = 80
    //波浪比例
    private val waveBit = 1 / 4f//1:1
    //波浪颜色
    private var mWavePaintColor: Int = Color.RED

    init {
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_test)
        init()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        mWidth = widthSize
        mHeight = heightSize

        setMeasuredDimension(mWidth, mHeight)
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)

        mWaveCount = Math.round(mHeight / mWaveLength + 1.5).toInt()
        mCenterY = (mHeight / 100 * (100 - progerss)).toFloat()
        mWaveLength = 700
        bitmap = changeSize(bitmap)
    }


    fun setColor(c: Int) {
        mWavePaint!!.setColor(c)
    }

    fun setProgerss(c: Int) {
        this.progerss = c
    }


    private fun init() {
        mWavePaint = Paint()
        mWavePaint?.color = mWavePaintColor
        mWavePaint?.isAntiAlias = true
        mWavePaint?.xfermode = mMode0

        bitmapPaint = Paint()
        bitmapPaint?.isAntiAlias = true

        mPath = Path()

        val animator = ValueAnimator.ofInt(0, mWaveLength)
        animator.duration = 1000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            mOffset = animation.animatedValue as Int
            postInvalidate()
        }
        animator.start()


    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mBitmap!!.eraseColor(Color.parseColor("#00000000"))

        mPath?.run {
            reset()
            moveTo((-mWaveLength + mOffset).toFloat(), mCenterY)
            for (i in 0 until mWaveCount) {//50是波纹的大小
                quadTo(
                    -mWaveLength * (1 - waveBit) + (i * mWaveLength).toFloat() + mOffset.toFloat(),
                    mCenterY + mWaveHeight,
                    (-mWaveLength / 2 + i * mWaveLength + mOffset).toFloat(),
                    mCenterY
                )
                quadTo(
                    -mWaveLength * waveBit + (i * mWaveLength).toFloat() + mOffset.toFloat(),
                    mCenterY - mWaveHeight,
                    (i * mWaveLength + mOffset).toFloat(),
                    mCenterY
                )
            }

            lineTo(mWidth.toFloat(), mHeight.toFloat())
            lineTo(0f, mHeight.toFloat())
            close()
        }


        if (bitmap != null)
            mCanvas?.drawBitmap(bitmap!!, 0f, 0f, bitmapPaint)
        mCanvas?.drawPath(mPath!!, mWavePaint!!)

        canvas.drawBitmap(mBitmap!!, 0.toFloat(), 0.toFloat(), null)

    }

    fun setMode(c: Int) {

        when (c) {
            0 -> mWavePaint!!.setXfermode(mMode0)
            1 -> mWavePaint!!.setXfermode(mMode1)
            2 -> mWavePaint!!.setXfermode(mMode2)
            3 -> mWavePaint!!.setXfermode(mMode3)
        }

    }

    fun changeSize(bm: Bitmap?): Bitmap {
        val width = bm!!.width
        val height = bm.height

        val scaleWidth = mWidth.toFloat() / width
        val scaleHeight = mHeight.toFloat() / height
        val matrix = Matrix()

        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)

    }
}