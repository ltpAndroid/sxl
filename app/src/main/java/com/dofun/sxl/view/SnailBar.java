package com.dofun.sxl.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

import com.dofun.sxl.R;

public class SnailBar extends AppCompatSeekBar {
    public SnailBar(Context context) {
        super(context);
        init();
    }


    public SnailBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SnailBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setMax(300);
        this.setThumbOffset(dip2px(getContext(), 20));
        //this.setBackgroundResource(R.drawable.sbg);
        int padding = dip2px(getContext(), (float) 10);
        this.setPadding(padding, padding, padding, padding);
        this.setProgressDrawable(getResources().getDrawable(R.drawable.snailbar_define_style));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        AnimationDrawable drawable = (AnimationDrawable) this.getThumb();
        drawable.start();
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
