package com.wairdell.learnhelp.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wairdell.learnhelp.R;

/**
 * author : fengqiao
 * date   : 2023/4/7 14:12
 * desc   :
 */
public class FragmentMockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_mock);
        if (savedInstanceState == null) {
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setId(View.generateViewId());
            ((ViewGroup) findViewById(R.id.container_root)).addView(frameLayout);
            getSupportFragmentManager()
                    .beginTransaction()
//                .add(R.id.container_fragment, new MockFragment())
                    .add(frameLayout.getId(), new MockFragment(), "tag")
                    .commit();
        }

    }
}
