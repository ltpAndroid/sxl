package com.dofun.sxl.activity;

import android.os.Bundle;

import com.dofun.sxl.R;

public class LysListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lys_list);

        setStateBarColor();
    }

}
