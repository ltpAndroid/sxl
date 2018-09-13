package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.NoticeAdapter;
import com.dofun.sxl.bean.Notice;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
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

public class NoticeListActivity extends BaseActivity {

    @BindView(R.id.iv_back_notice_list)
    ImageView ivBack;
    @BindView(R.id.rv_notice)
    RecyclerView rvNotice;
    @BindView(R.id.refresh_notice)
    SmartRefreshLayout refreshNotice;

    private NoticeAdapter adapter;
    private List<Notice> noticeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNotice.setLayoutManager(manager);
        rvNotice.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        refreshNotice.setRefreshHeader(new ClassicsHeader(this))
                .setRefreshFooter(new ClassicsFooter(this));
    }

    private void setListener() {
        refreshNotice.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //TODO
                refreshLayout.finishLoadMore(1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Notice notice = (Notice) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("notice", notice);
                ActivityUtils.startActivity(bundle, NoticeDetailActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        JSONObject param = new JSONObject();
        param.put("type", 1);
        HttpUs.send(Deploy.queryNoticeList(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                noticeList = JSONArray.parseArray(info.getData(), Notice.class);
                if (adapter == null) {
                    adapter = new NoticeAdapter(R.layout.item_notice_list, noticeList);
                    rvNotice.setAdapter(adapter);
                } else {
                    adapter.replaceData(noticeList);
                }
                setListener();
                refreshNotice.finishRefresh();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
                refreshNotice.finishRefresh();
            }
        });
    }

    @OnClick(R.id.iv_back_notice_list)
    public void onViewClicked() {
        finish();
    }
}
