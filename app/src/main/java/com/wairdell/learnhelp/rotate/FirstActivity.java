package com.wairdell.learnhelp.rotate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wairdell.learnhelp.R;

/**
 * author : fengqiao
 * date   : 2023/3/28 14:26
 * desc   :
 */
public class FirstActivity extends Activity implements View.OnClickListener {

    private ActivityRotation3DAnimationHelper rotationHelper;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        ImageView first_btn = (ImageView) findViewById(R.id.first_btn);
        first_btn.setOnClickListener(this);
        Intent intent = new Intent(this, SecondActivity.class);
        rotationHelper = ActivityRotation3DAnimationHelper.withExitActivity(this, intent);
    }

    @Override
    public void onClick(View v) {
        rotationHelper.start();
    }

}