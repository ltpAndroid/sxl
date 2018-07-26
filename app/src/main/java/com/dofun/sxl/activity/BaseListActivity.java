package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.ListBeanAdapter;
import com.dofun.sxl.bean.ListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseListActivity extends BaseActivity {

    @BindView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.back_to_main)
    ImageView backToMain;

    public ListBeanAdapter adapter;
    public List<ListBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvType.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_height));
        rvType.addItemDecoration(divider);
    }

    public abstract void initData();

    @OnClick(R.id.back_to_main)
    public void onViewClicked() {
        finish();
    }
}
