package com.wairdell.learnhelp;

import android.animation.Animator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

/**
 * author : fengqiao
 * date   : 2022/3/8 16:31
 * desc   :
 */
public class ProgressDrawableHelper {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setProgress(View view, int progress) {
        Drawable drawable = view.getBackground();
        ProgressDrawable progressDrawable;
        if (drawable instanceof ProgressDrawable) {
            progressDrawable = (ProgressDrawable) drawable;
        } else {
            progressDrawable = new ProgressDrawable(drawable);
            view.setBackground(progressDrawable);
        }
        progressDrawable.setProgress(progress);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setGlitter(View view) {
        Drawable drawable = view.getBackground();
        GlitterDrawable glitterDrawable;
        Animator animator;
        if (drawable instanceof GlitterDrawable) {
            glitterDrawable = (GlitterDrawable) drawable;
            animator = glitterDrawable.startAnimation();
        } else {
            glitterDrawable = new GlitterDrawable(drawable);
            view.setBackground(glitterDrawable);
            animator = glitterDrawable.startAnimation();
        }
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                animator.cancel();
            }
        });
    }

}
