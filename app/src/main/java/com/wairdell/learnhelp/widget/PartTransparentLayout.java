package com.wairdell.learnhelp.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author : fengqiao
 * date   : 2023/3/29 14:38
 * desc   :
 */
public class PartTransparentLayout extends FrameLayout {

    public PartTransparentLayout(@NonNull Context context) {
        this(context, null);
    }

    public PartTransparentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartTransparentLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class Builder {

        private Activity activity;
        private PartTransparentLayout layout;
        private int color = Color.parseColor("#66000000");

        public Builder(Activity activity) {
            this.activity = activity;
            layout = new PartTransparentLayout(activity);
        }

        public Builder withAnchorView(View view) {
            ImageView imageView = new ImageView(activity);
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            LayoutParams layoutParams = new LayoutParams(view.getWidth(), view.getHeight());
            layoutParams.leftMargin = location[0];
            layoutParams.topMargin = location[1] - getStatusBarHeight(activity);
            imageView.setImageBitmap(view2Bitmap(view));
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.performClick();
                }
            });
            layout.addView(imageView, layoutParams);
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public void show() {
            ViewGroup contentView = activity.findViewById(Window.ID_ANDROID_CONTENT);
            layout.setBackgroundColor(color);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            contentView.addView(layout, layoutParams);
        }

    }

    public static Bitmap view2Bitmap(final View view) {
        if (view == null) return null;
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (null == drawingCache || drawingCache.isRecycled()) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            drawingCache = view.getDrawingCache();
            if (null == drawingCache || drawingCache.isRecycled()) {
                bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                view.draw(canvas);
            } else {
                bitmap = Bitmap.createBitmap(drawingCache);
            }
        } else {
            bitmap = Bitmap.createBitmap(drawingCache);
        }
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        return bitmap;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId =
                context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
