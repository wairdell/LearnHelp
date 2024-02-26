package com.wairdell.learnhelp;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
 * date   : 2022/4/2 9:36
 * desc   :
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class GlitterDrawable extends DrawableWrapper {

    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path;
    private Path clipPath = new Path();
    private int dx;
    /**
     * Creates a new wrapper around the specified drawable.
     *
     * @param dr the drawable to wrap
     */
    public GlitterDrawable(@Nullable Drawable dr) {
        super(dr);
        paint.setColor(Color.parseColor("#99FFFFFF"));
        path = new Path();
        path.addPath(createPath(0, 12, getIntrinsicHeight(), 60));
        path.addPath(createPath(28, 74, getIntrinsicHeight(), 60));
        float roundReal = 12F;
        int padding = 12;
        clipPath.addRoundRect(padding, padding, getIntrinsicWidth() - padding, getIntrinsicHeight() - padding, new float[]{roundReal, roundReal, roundReal, roundReal, roundReal, roundReal, roundReal, roundReal}, Path.Direction.CW);
    }

    private Path createPath(int startX, int width, int height, int offset) {
        Path path = new Path();
        path.moveTo(startX, height);
        path.lineTo(startX + width, height);
        path.lineTo(startX + width + offset, 0);
        path.lineTo(startX + offset, 0);
        path.close();
        return path;
    }


    private ObjectAnimator animator;

    public ObjectAnimator startAnimation() {
        if (animator != null) {
            animator.cancel();
        }
        animator = ObjectAnimator.ofInt(this, "dx", -74, getDrawable().getIntrinsicWidth());
        animator.setDuration(800);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
        return animator;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        paint.setXfermode(xfermode);

        canvas.save();
        canvas.clipPath(clipPath);
        canvas.translate(dx, 0);
        canvas.drawPath(path, paint);
        canvas.translate(-dx, 0);
        canvas.restore();

        paint.setXfermode(null);
        canvas.restore();
    }
}
