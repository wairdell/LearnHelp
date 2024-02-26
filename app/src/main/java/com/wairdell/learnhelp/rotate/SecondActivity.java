package com.wairdell.learnhelp.rotate;

import android.app.Activity;
import android.os.Bundle;

import com.wairdell.learnhelp.R;

/**
 * author : fengqiao
 * date   : 2023/3/28 14:27
 * desc   :
 */
public class SecondActivity extends Activity {

    private ActivityRotation3DAnimationHelper rotationHelper;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        rotationHelper = ActivityRotation3DAnimationHelper.withEnterActivity(this, null);
    }

}