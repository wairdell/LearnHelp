package com.wairdell.learnhelp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.ReplacementSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author : fengqiao
 * date   : 2022/2/10 10:41
 * desc   :
 */
public class CustomUnderlineSpan extends ReplacementSpan {

    private int size;
    private Layout layout;
    private Paint underlinePaint = new Paint();

    public CustomUnderlineSpan() {
        underlinePaint.setColor(Color.parseColor("#1FFF1717"));
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        size = (int) paint.measureText(text, start, end);
        Log.e("TAG", "start " + start + " , end = " + end + " size = " + size);
        return size;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        canvas.drawText(text, start, end, x, y, paint);
        int underlineBottom = bottom + 6;
        canvas.drawRect(x, underlineBottom - 24, x + size, underlineBottom, underlinePaint);
    }
}
