package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.ListBeanAdapter;
import com.dofun.sxl.bean.ListBean;
import com.dofun.sxl.bean.MistakeNote;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MistakeListActivity extends BaseActivity {

    @BindView(R.id.finish_from_sjd)
    ImageView ivBack;
    @BindView(R.id.rv_sjd_list)
    RecyclerView rvMistake;

    private ListBeanAdapter adapter;
    private List<ListBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjd_list);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMistake.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_height));
        rvMistake.addItemDecoration(divider);
    }

    private void initData() {
        MistakeNote note = (MistakeNote) getIntent().getSerializableExtra("mistakeNote");

        JSONObject param = new JSONObject();
        param.put("homeworkId", note.getHomeworkId());
        HttpUs.send(Deploy.getCurrentWrongBook(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());

            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    @OnClick(R.id.finish_from_sjd)
    public void onViewClicked() {
        finish();
    }
}
