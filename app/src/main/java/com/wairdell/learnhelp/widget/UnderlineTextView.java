package com.wairdell.learnhelp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.wairdell.learnhelp.R;


@SuppressLint("AppCompatCustomView")
public class UnderlineTextView extends TextView {

    private Rect mRect;
    private Paint mPaint;
    private int mColor;
    private float density;
    private float mStrokeWidth;
    private float mLineBottomMargin = 0;

    public UnderlineTextView(Context context) {
        this(context, null, 0);
    }

    public UnderlineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //获取屏幕密度
        density = context.getResources().getDisplayMetrics().density;
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinedTextView, defStyleAttr, 0);
        mColor = array.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
        mStrokeWidth = array.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2);
        mLineBottomMargin = array.getDimension(R.styleable.UnderlinedTextView_underlineBottomMargin, 0);
        setPadding(getLeft(), getTop(), getRight(), getBottom());
        array.recycle();

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到TextView显示有多少行
        int count = getLineCount();
        //得到TextView的布局
        final Layout layout = getLayout();


        float left, right;
        int firstCharInLine;

        for (int i = 0; i < count; i++) {
            //getLineBounds得到这一行的外包矩形,这个字符的顶部Y坐标就是rect的top 底部Y坐标就是rect的bottom
            int lineBounds = getLineBounds(i, mRect);
            int top = (int) (mRect.bottom - mStrokeWidth + mLineBottomMargin - (i == count - 1 ? 0 : layout.getSpacingAdd()));
            firstCharInLine = layout.getLineStart(i);
            //要得到这个字符的左边X坐标 用layout.getPrimaryHorizontal
            //得到字符的右边X坐标用layout.getSecondaryHorizontal
            if (count > 1 && i == 0 && getText().toString().matches("^1(.|、).+")) {
                left = layout.getPrimaryHorizontal(firstCharInLine + 2);
            } else {
                left = layout.getPrimaryHorizontal(firstCharInLine);
            }
            right = layout.getPrimaryHorizontal(firstCharInLine) + layout.getLineWidth(i);
            canvas.drawRect(left, top, right, top + mStrokeWidth, mPaint);
        }
        super.onDraw(canvas);

    }

    public int getUnderLineColor() {
        return mColor;
    }

    public void setUnderLineColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, (int) (bottom + mLineBottomMargin));
    }

    public float getUnderlineWidth() {
        return mStrokeWidth;
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }

}

