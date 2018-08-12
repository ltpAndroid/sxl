package com.dofun.sxl.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.DailyPraciseActivity;
import com.dofun.sxl.activity.LysListActivity;
import com.dofun.sxl.activity.SjdListActivity;
import com.dofun.sxl.activity.StatisticsActivity;
import com.dofun.sxl.activity.StudyToolActivity;
import com.dofun.sxl.activity.XhzListActivity;
import com.dofun.sxl.adapter.PractiseAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.GlideImageLoader;
import com.dofun.sxl.view.DialogWaiting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.rv_practise)
    RecyclerView rvPractise;

    Unbinder unbinder;
    @BindView(R.id.study_tool)
    LinearLayout studyTool;
    @BindView(R.id.grade_report)
    LinearLayout gradeReport;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.refresh_main)
    SmartRefreshLayout refreshMain;

    private PractiseAdapter adapter;
    private ArrayList<DailyPractise> practises = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // setTitleStr("诵习练-学生版",false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片集合
        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            images.add(R.drawable.banner_main);
        }
        banner.setImages(images);
        //轮播间隔时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPractise.setLayoutManager(manager);
        rvPractise.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        refreshMain.setRefreshHeader(new ClassicsHeader(mActivity));
    }

    private void initData() {
        askData();

    }

    private void askData() {
        final DialogWaiting dialog = DialogWaiting.build(mActivity);
        dialog.show();

        HttpUs.send(Deploy.getDailyPractise(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.getData());

                practises = (ArrayList<DailyPractise>) JSONArray.parseArray(info.getData(), DailyPractise.class);
                List<DailyPractise> list = new ArrayList<>();
                if (practises.size() > 3) {
                    tvMore.setText("查看更多练习");
                    tvMore.setEnabled(true);
                    list.add(practises.get(0));
                    list.add(practises.get(1));
                    list.add(practises.get(2));
                } else {
                    if (practises.size() == 0) {
                        tvMore.setText("需要老师布置练习再刷新");
                        tvMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                askData();
                            }
                        });
                    } else {
                        tvMore.setText("没有更多练习");
                        tvMore.setEnabled(false);
                        list = practises;
                    }
                }

                if (adapter == null) {
                    adapter = new PractiseAdapter(R.layout.item_practise, list);
                    rvPractise.setAdapter(adapter);
                } else {
                    adapter.replaceData(list);
                }
                dialog.dimiss();
                setListener();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.getMsg());
                showTip(info.getMsg());
            }
        });
    }

    private void setListener() {
        refreshMain.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                askData();
                refreshLayout.finishRefresh();
            }
        });
        refreshMain.setEnableLoadMore(false);

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

                //                Bundle bundle = new Bundle();
                //                bundle.putSerializable("dailyPractise", dailyPractise);
                //                ActivityUtils.startActivity(bundle, SjdListActivity.class);
            }
        });
    }

    @OnClick({R.id.study_tool, R.id.grade_report, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.study_tool:
                ActivityUtils.startActivity(StudyToolActivity.class);
                break;
            case R.id.grade_report:
                ActivityUtils.startActivity(StatisticsActivity.class);
                break;
            case R.id.tv_more:
                Bundle bundle = new Bundle();
                bundle.putSerializable("practises", practises);
                ActivityUtils.startActivity(bundle, DailyPraciseActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
