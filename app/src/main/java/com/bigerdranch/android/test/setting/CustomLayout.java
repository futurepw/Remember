package com.bigerdranch.android.test.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
/**
 * Created by Administrator on 2017/12/4.
 */

public class CustomLayout extends ViewGroup {

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
