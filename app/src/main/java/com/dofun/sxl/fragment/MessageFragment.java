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
import com.dofun.sxl.activity.ChatActivity;
import com.dofun.sxl.adapter.MessageAdapter;
import com.dofun.sxl.bean.MessageBean;
import com.dofun.sxl.bean.TermBean;
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
public class MessageFragment extends BaseFragment {
    private List<MessageBean> messageBeanList = new ArrayList<>();
    private MessageAdapter adapter;

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.refresh_message)
    SmartRefreshLayout refreshMessage;
    Unbinder unbinder;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
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
        rvMessage.setLayoutManager(manager);
        rvMessage.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        refreshMessage.setRefreshHeader(new ClassicsHeader(mActivity))
                .setRefreshFooter(new ClassicsFooter(mActivity));

        refreshMessage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        TermBean termBean = (TermBean) SPUtils.getBaseBean(SPUtils.TERM, TermBean.class);
        param.put("schoolId ", String.valueOf(userInfo.getSchoolId()));
        param.put("classId ", String.valueOf(userInfo.getClassId()));
        param.put("grade", String.valueOf(termBean.getGrade()));
        param.put("termId", String.valueOf(termBean.getId()));
        param.put("roleType", "2");
        HttpUs.send(Deploy.queryInteractionList(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                messageBeanList = JSONArray.parseArray(info.getData(), MessageBean.class);
                bindData(messageBeanList);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void bindData(List<MessageBean> beanList) {
        if (adapter == null) {
            adapter = new MessageAdapter(R.layout.item_msg_list, beanList);
            rvMessage.setAdapter(adapter);
        } else {
            adapter.replaceData(beanList);
        }

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageBean messageBean = (MessageBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("messageBean", messageBean);
                ActivityUtils.startActivity(bundle, ChatActivity.class);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
