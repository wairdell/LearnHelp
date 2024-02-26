package com.wairdell.learnhelp.rotate;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ActivityRotation3DAnimationHelper {

    public static final String EXTRA_ROTATION_TAG_KEY = "extra_rotation_tag_key";

    private static final Map<String, ApplyNextAnimationRunnable> APPLY_NEXT_ANIMATION_MAP = new HashMap<>();

    public static ActivityRotation3DAnimationHelper withExitActivity(Activity exitActivity, Intent intent) {
        ActivityRotation3DAnimationHelper animationHelper = new ActivityRotation3DAnimationHelper(exitActivity, false);
        animationHelper.setIntent(intent);
        return animationHelper;
    }

    public static ActivityRotation3DAnimationHelper withEnterActivity(Activity exitActivity, Runnable completeListener) {
        ActivityRotation3DAnimationHelper animationHelper = new ActivityRotation3DAnimationHelper(exitActivity, true);
        animationHelper.setCompleteListener(completeListener);
        return animationHelper;
    }

    private final boolean isEnterMode;
    private final Activity activity;
    private final String tagKey;
    private Intent intent;
    private Runnable completeListener;

    private ActivityRotation3DAnimationHelper(Activity activity, boolean isEnterMode) {
        this.activity = activity;
        this.isEnterMode = isEnterMode;
        if (isEnterMode) {
            tagKey = activity.getIntent().getStringExtra(EXTRA_ROTATION_TAG_KEY);
            activity.findViewById(Window.ID_ANDROID_CONTENT).setVisibility(View.INVISIBLE);
            APPLY_NEXT_ANIMATION_MAP.put(tagKey, new ApplyNextAnimationRunnable(this));
        } else {
            tagKey = UUID.randomUUID().toString();
        }
    }

    private void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void setCompleteListener(Runnable completeListener) {
        this.completeListener = completeListener;
    }

    public void start() {
        if (isEnterMode) {
            activity.findViewById(Window.ID_ANDROID_CONTENT).setVisibility(View.VISIBLE);
            applyEnterRotation();
        } else {
            applyExitRotation();
        }
    }

    private void applyExitRotation() {
        ViewGroup container = activity.findViewById(Window.ID_ANDROID_CONTENT);
        Rotate3DAnimation rotation = createAnimation(container, 0, -90, true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationStart(Animation animation) {
                intent.putExtra(EXTRA_ROTATION_TAG_KEY, tagKey);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notifyEnterAnimation();
                activity.finish();
                activity.overridePendingTransition(0, 0);
            }
        });
        container.startAnimation(rotation);
    }

    private void notifyEnterAnimation() {
        ApplyNextAnimationRunnable animationListener = APPLY_NEXT_ANIMATION_MAP.get(tagKey);
        if (animationListener != null) {
            animationListener.run();
            APPLY_NEXT_ANIMATION_MAP.remove(tagKey);
        }
    }

    private void applyEnterRotation() {
        ViewGroup container = activity.findViewById(Window.ID_ANDROID_CONTENT);
        Rotate3DAnimation rotation = createAnimation(container, 90, 0, false);
        rotation.setInterpolator(new DecelerateInterpolator());
        rotation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                if (completeListener != null) completeListener.run();
            }
        });
        container.startAnimation(rotation);
    }

    private Rotate3DAnimation createAnimation(ViewGroup container, float start, float end, boolean reverse) {
        final float centerX = container.getWidth() / 2.0f;
        final float centerY = container.getHeight() / 2.0f;
        final Rotate3DAnimation rotation = new Rotate3DAnimation(container.getContext(), start, end, centerX, centerY, 310.0f, reverse);
        rotation.setDuration(600);
        rotation.setFillAfter(true);
        return rotation;
    }

    private static class ApplyNextAnimationRunnable implements Runnable {

        private final WeakReference<ActivityRotation3DAnimationHelper> rotationHelperRef;

        public ApplyNextAnimationRunnable(ActivityRotation3DAnimationHelper rotationHelper) {
            rotationHelperRef = new WeakReference<>(rotationHelper);
        }

        @Override
        public void run() {
            ActivityRotation3DAnimationHelper rotationHelper = rotationHelperRef.get();
            if (rotationHelper != null) {
                rotationHelper.start();
            }
        }
    }

    private static class AnimationListenerAdapter implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {}

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {}
    }

}