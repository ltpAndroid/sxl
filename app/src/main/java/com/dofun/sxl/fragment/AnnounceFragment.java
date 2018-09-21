package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.AnnounceDetailActivity;
import com.dofun.sxl.adapter.AnnounceAdapter;
import com.dofun.sxl.bean.Announce;
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
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnounceFragment extends BaseFragment {

    @BindView(R.id.rv_announce)
    RecyclerView rvAnnounce;
    @BindView(R.id.refresh_announce)
    SmartRefreshLayout refreshAnnounce;
    Unbinder unbinder;

    private AnnounceAdapter adapter;
    private List<Announce> announceList = new ArrayList<>();

    public AnnounceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announce, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAnnounce.setLayoutManager(manager);
        rvAnnounce.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        refreshAnnounce.setRefreshHeader(new ClassicsHeader(mActivity))
                .setRefreshFooter(new ClassicsFooter(mActivity));

        refreshAnnounce.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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

    private void initData() {
        JSONObject param = new JSONObject();
        UserInfo userInfo = (UserInfo) SPUtils.getBaseBean(SPUtils.USER, UserInfo.class);
        param.put("schoolId ", String.valueOf(userInfo.getSchoolId()));
        param.put("classId ", String.valueOf(userInfo.getClassId()));
        HttpUs.send(Deploy.queryDataList(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                announceList = JSONArray.parseArray(info.getData(), Announce.class);
                if (adapter == null) {
                    adapter = new AnnounceAdapter(R.layout.item_comment, announceList);
                    rvAnnounce.setAdapter(adapter);
                } else {
                    adapter.replaceData(announceList);
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
                Announce announce = (Announce) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("announce", announce);
                ActivityUtils.startActivity(bundle, AnnounceDetailActivity.class);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
