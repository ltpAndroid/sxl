package com.dofun.sxl.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.XhzListAdapter;
import com.dofun.sxl.bean.XhzListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XhzListActivity extends BaseActivity {

    @BindView(R.id.finish_from_xhz)
    ImageView ivBack;
    @BindView(R.id.rv_xhz)
    RecyclerView rvXhz;
    @BindView(R.id.refresh_xhz)
    SmartRefreshLayout refreshXhz;

    private XhzListAdapter adapter;
    private List<XhzListBean> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xhz_list);
        ButterKnife.bind(this);

        setStateBarColor();
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvXhz.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_height));
        rvXhz.addItemDecoration(divider);

        refreshXhz.setRefreshHeader(new ClassicsHeader(this))
                .setRefreshFooter(new ClassicsFooter(this));
    }

    private void initData() {
        beanList = new ArrayList<>();
        beanList.add(new XhzListBean("2018-07-13", "第一周  -1", "10", "0.3", "左窄右宽，左短右长"));
        beanList.add(new XhzListBean("2018-07-13", "第二周  -1", "10", "0.3", "左窄右宽，左短右长"));
        beanList.add(new XhzListBean("2018-07-13", "第一周  -1", "10", "0.3", "左窄右宽，左短右长"));
        beanList.add(new XhzListBean("2018-07-13", "第二周  -1", "10", "0.3", "左窄右宽，左短右长"));
        beanList.add(new XhzListBean("2018-07-13", "第一周  -1", "10", "0.3", "左窄右宽，左短右长"));
        beanList.add(new XhzListBean("2018-07-13", "第二周  -1", "10", "0.3", "左窄右宽，左短右长"));
        beanList.add(new XhzListBean("2018-07-13", "第一周  -1", "10", "0.3", "左窄右宽，左短右长"));

        if (adapter == null) {
            adapter = new XhzListAdapter(R.layout.item_xhz_list, beanList);
            rvXhz.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }


        refreshXhz.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                adapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }
        });
    }

    @OnClick(R.id.finish_from_xhz)
    public void onViewClicked() {
        finish();
    }
}
