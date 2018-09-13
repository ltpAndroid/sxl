package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.InteractAdapter;
import com.dofun.sxl.bean.Interact;
import com.dofun.sxl.bean.UserInfo;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.SPUtils;
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

public class InteractListActivity extends BaseActivity {

    @BindView(R.id.tv_back_interact)
    TextView tvBack;
    @BindView(R.id.rv_interact)
    RecyclerView rvInteract;
    @BindView(R.id.refresh_interact)
    SmartRefreshLayout refreshInteract;

    private List<Interact> interactList = new ArrayList<>();
    private InteractAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_list);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvInteract.setLayoutManager(manager);
        rvInteract.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        refreshInteract.setRefreshHeader(new ClassicsHeader(this))
                .setRefreshFooter(new ClassicsFooter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        JSONObject param = new JSONObject();
        UserInfo userInfo = (UserInfo) SPUtils.getBaseBean(SPUtils.USER, UserInfo.class);
        param.put("schoolId ", String.valueOf(userInfo.getSchoolId()));
        param.put("classId ", String.valueOf(userInfo.getClassId()));
        HttpUs.send(Deploy.queryDataList(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                interactList = JSONArray.parseArray(info.getData(), Interact.class);
                if (adapter == null) {
                    adapter = new InteractAdapter(R.layout.item_comment, interactList);
                    rvInteract.setAdapter(adapter);
                } else {
                    adapter.replaceData(interactList);
                }
                setListener();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Interact interact = (Interact) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("interact", interact);
                ActivityUtils.startActivity(bundle, InteractDetailActivity.class);
            }
        });


        refreshInteract.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                initData();
            }
        });
    }

    @OnClick(R.id.tv_back_interact)
    public void onViewClicked() {
        finish();
    }
}
