package com.wairdell.learnhelp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * author : fengqiao
 * date   : 2022/2/14 17:26
 * desc   :
 */
@SuppressLint("AppCompatCustomView")
public class ProgressImageView extends ImageView {

    private Paint paint;


    public ProgressImageView(Context context) {
        super(context);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        super.onDraw(canvas);
        canvas.restore();
    }
}
