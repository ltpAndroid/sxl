package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.PractiseAdapter;
import com.dofun.sxl.bean.DailyPractise;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyPraciseActivity extends BaseActivity {

    @BindView(R.id.iv_back_daily)
    ImageView ivBack;
    @BindView(R.id.rv_daily)
    RecyclerView rvDaily;

    private PractiseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pracise);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDaily.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvDaily.setLayoutManager(manager);
    }

    private void initData() {
        List<DailyPractise> practiseList = (List<DailyPractise>) getIntent().getSerializableExtra("practises");
        if (adapter == null) {
            adapter = new PractiseAdapter(R.layout.item_practise, practiseList);
            rvDaily.setAdapter(adapter);
        } else {
            adapter.replaceData(practiseList);
        }
        setListener();
    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DailyPractise dailyPractise = (DailyPractise) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dailyPractise", dailyPractise);
                String title = dailyPractise.getCourseName();
                if (title.equals("诵经典")) {
                    ActivityUtils.startActivity(bundle, SjdListActivity.class);
                } else if (title.equals("习汉字")) {
                    ActivityUtils.startActivity(bundle, XhzListActivity.class);
                } else if (title.equals("练运算")) {
                    ActivityUtils.startActivity(bundle, LysListActivity.class);
                }
            }
        });
    }

    @OnClick(R.id.iv_back_daily)
    public void onViewClicked() {
        finish();
    }
}
