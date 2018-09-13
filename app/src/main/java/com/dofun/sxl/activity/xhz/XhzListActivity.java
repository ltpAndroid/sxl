package com.dofun.sxl.activity.xhz;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.DailyPractise;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XhzListActivity extends BaseActivity {


    @BindView(R.id.finish_from_sjd)
    ImageView ivBack;
    @BindView(R.id.iv_start)
    ImageView ivStart;
    private DailyPractise dailyPractise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjd_list);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        dailyPractise = (DailyPractise) getIntent().getSerializableExtra("dailyPractise");
    }

    @OnClick({R.id.finish_from_sjd, R.id.iv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_from_sjd:
                finish();
                break;
            case R.id.iv_start:
                Bundle bundle = new Bundle();
                int homeworkId = dailyPractise.getId();
                int fkId = dailyPractise.getCourseId();
                bundle.putInt("homeworkId", homeworkId);
                bundle.putInt("fkId", fkId);
                ActivityUtils.startActivity(bundle, WriteWordActivity.class);
                break;
        }
    }
}
