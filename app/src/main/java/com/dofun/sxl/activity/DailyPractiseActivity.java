package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.lys.LysKindActivity;
import com.dofun.sxl.activity.sjd.SjdKindActivity;
import com.dofun.sxl.activity.xhz.XhzListActivity;
import com.dofun.sxl.adapter.PractiseAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DailyPractiseActivity extends BaseActivity {

    @BindView(R.id.iv_back_daily)
    ImageView ivBack;
    @BindView(R.id.rv_daily)
    RecyclerView rvDaily;

    private PractiseAdapter adapter;
    List<DailyPractise> practiseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pracise);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDaily.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvDaily.setLayoutManager(manager);

        //        refreshDaily.setRefreshHeader(new ClassicsHeader(this))
        //                .setRefreshFooter(new ClassicsFooter(this));
    }

    private void initData() {
        practiseList = (List<DailyPractise>) getIntent().getSerializableExtra("practises");
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
                    ActivityUtils.startActivity(bundle, SjdKindActivity.class);
                } else if (title.equals("习汉字")) {
                    ActivityUtils.startActivity(bundle, XhzListActivity.class);
                } else if (title.equals("练运算")) {
                    ActivityUtils.startActivity(bundle, LysKindActivity.class);
                }
            }
        });

        //        refreshDaily.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
        //            @Override
        //            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //                askData();
        //                refreshLayout.finishLoadMore(1500);
        //            }
        //
        //            @Override
        //            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //                askData();
        //                refreshLayout.finishRefresh(1500);
        //            }
        //        });
    }

    private void askData() {
        HttpUs.send(Deploy.getDailyPractise(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.getData());

                practiseList = JSONArray.parseArray(info.getData(), DailyPractise.class);

                if (adapter == null) {
                    adapter = new PractiseAdapter(R.layout.item_practise, practiseList);
                    rvDaily.setAdapter(adapter);
                } else {
                    adapter.replaceData(practiseList);
                }
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.getMsg());
                showTip(info.getMsg());
            }
        });
    }

    @OnClick(R.id.iv_back_daily)
    public void onViewClicked() {
        finish();
    }


    @Override
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        if ((int) bean.getTag() == EventConstants.FINISH_CODE) {
            switch (bean.getCode()) {
                case EventConstants.FINISH:
                    finish();
                    break;
            }
        }
    }
}
