package com.dofun.sxl.activity.personal;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.adapter.RewardAdapter;
import com.dofun.sxl.bean.RewardBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonRewardActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.rv_praise)
    RecyclerView rvPraise;
    @BindView(R.id.refresh_praise)
    SmartRefreshLayout refreshPraise;

    private RewardAdapter adapter;
    private List<RewardBean> beanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_reward);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPraise.setLayoutManager(manager);
        rvPraise.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        beanList.add(new RewardBean("1", R.drawable.rank_header, "王富华", "21", "58"));
        beanList.add(new RewardBean("2", R.drawable.rank_header, "张磊", "21", "58"));
        beanList.add(new RewardBean("3", R.drawable.rank_header, "刘羽", "21", "58"));
        beanList.add(new RewardBean("4", R.drawable.rank_header, "颜小明", "21", "58"));
        beanList.add(new RewardBean("5", R.drawable.rank_header, "曾鹏", "21", "58"));
        beanList.add(new RewardBean("6", R.drawable.rank_header, "董博文", "21", "58"));

        if (adapter == null) {
            adapter = new RewardAdapter(R.layout.item_praise_list, beanList);
            rvPraise.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
