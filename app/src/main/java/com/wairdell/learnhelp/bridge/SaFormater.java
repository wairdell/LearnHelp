package com.wairdell.learnhelp.bridge;

import android.content.Context;

import java.util.Map;

/**
 * author : fengqiao
 * date   : 2022/5/30 14:04
 * desc   :
 */
public class SaFormater {

    static {
        System.loadLibrary("sabridge");
    }

    public native void format(Map<String, Object> params);

    public native void verify(Context context);

}
