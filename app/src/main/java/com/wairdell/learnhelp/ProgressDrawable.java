package com.wairdell.learnhelp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * author : fengqiao
 * date   : 2022/3/8 16:12
 * desc   :
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class ProgressDrawable extends DrawableWrapper {

    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int progress = 50;

    public ProgressDrawable(@Nullable Drawable dr) {
        super(dr);
        paint.setColor(Color.parseColor("#4D000000"));
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        paint.setXfermode(xfermode);
        canvas.drawRect(getDrawable().getIntrinsicWidth() * (progress / 100f), 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight(), paint);
        paint.setXfermode(null);
        canvas.restore();
    }
}
