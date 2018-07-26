package com.dofun.sxl.activity;

import android.os.Bundle;

import com.dofun.sxl.R;

import butterknife.ButterKnife;

public class PersonalLevelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_level);
        ButterKnife.bind(this);
        setStateBarColor();

    }

}
