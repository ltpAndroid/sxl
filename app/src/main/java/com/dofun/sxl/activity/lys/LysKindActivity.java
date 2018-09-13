package com.dofun.sxl.activity.lys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.constant.EventConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LysKindActivity extends BaseActivity {

    @BindView(R.id.finish_from_lys)
    ImageView ivBack;
    @BindView(R.id.kind_layout_1)
    LinearLayout kindLayout1;
    @BindView(R.id.kind_layout_2)
    LinearLayout kindLayout2;
    @BindView(R.id.kind_layout_3)
    LinearLayout kindLayout3;
    @BindView(R.id.kind_layout_4)
    LinearLayout kindLayout4;

    private DailyPractise dailyPractise;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lys_kind);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        dailyPractise = (DailyPractise) getIntent().getSerializableExtra("dailyPractise");
    }

    @OnClick({R.id.finish_from_lys, R.id.kind_layout_1, R.id.kind_layout_2, R.id.kind_layout_3, R.id.kind_layout_4})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        int homeworkId = dailyPractise.getId();
        int fkId = dailyPractise.getCourseId();
        switch (view.getId()) {
            case R.id.finish_from_lys:
                finish();
                break;
            case R.id.kind_layout_1:
                position = 0;
                bundle.putInt("homeworkId", homeworkId);
                bundle.putInt("fkId", fkId);
                bundle.putInt("position", position);
                ActivityUtils.startActivity(bundle, LysDetailActivity.class);
                break;
            case R.id.kind_layout_2:
                position = 1;
                showTip("请从第1题开始做");
                break;
            case R.id.kind_layout_3:
                position = 2;
                showTip("请从第1题开始做");
                break;
            case R.id.kind_layout_4:
                position = 3;
                showTip("请从第1题开始做");
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
                LysKindActivity.this.finish();
                break;
        }
    }
}
