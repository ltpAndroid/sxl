package com.dofun.sxl.activity.sjd;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.constant.EventConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SjdKindActivity extends BaseActivity {


    @BindView(R.id.iv_sjd_kind)
    ImageView ivBack;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.view_4)
    View view4;
    @BindView(R.id.view_5)
    View view5;
    @BindView(R.id.view_6)
    View view6;
    @BindView(R.id.view_7)
    View view7;

    private DailyPractise dailyPractise;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sjd_kind);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        dailyPractise = (DailyPractise) getIntent().getSerializableExtra("dailyPractise");
    }

    @OnClick({R.id.view_1, R.id.view_2, R.id.view_3, R.id.view_4, R.id.view_5, R.id.view_6, R.id.view_7, R.id.iv_sjd_kind})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        int homeworkId = dailyPractise.getId();
        int fkId = dailyPractise.getCourseId();
        bundle.putInt("homeworkId", homeworkId);
        bundle.putInt("fkId", fkId);
        switch (view.getId()) {
            case R.id.view_1:
                position = 0;
                bundle.putInt("position", position);
                ActivityUtils.startActivity(bundle, SjdDetailActivity.class);
                break;
            case R.id.view_2:
            case R.id.view_3:
            case R.id.view_4:
            case R.id.view_5:
            case R.id.view_6:
                showTip("请从第1题开始做");
                //ActivityUtils.startActivity(bundle, ReciteActivity.class);
                break;
            case R.id.view_7:
                //showTip("暂无此题");
                ActivityUtils.startActivity(VideoRecordActivity.class);
                break;
            case R.id.iv_sjd_kind:
                finish();
                break;
        }
    }


    @Override
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.FINISH:
                SjdKindActivity.this.finish();
                break;
        }
    }
}
