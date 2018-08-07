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

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.SjdListActivity;
import com.dofun.sxl.activity.StatisticsActivity;
import com.dofun.sxl.activity.StudyToolActivity;
import com.dofun.sxl.adapter.PractiseAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.GlideImageLoader;
import com.dofun.sxl.view.DialogWaiting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
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

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    Unbinder unbinder;
    @BindView(R.id.study_tool)
    LinearLayout studyTool;
    @BindView(R.id.grade_report)
    LinearLayout gradeReport;
    private PractiseAdapter adapter;
    private List<DailyPractise> practises = new ArrayList<>();

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
        List<Integer> images = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            images.add(R.drawable.banner_main);
        }
        banner.setImages(images);
        //轮播间隔时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


        refresh.setRefreshFooter(new ClassicsFooter(mActivity))
                .setRefreshHeader(new ClassicsHeader(mActivity));

        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPractise.setLayoutManager(manager);
        rvPractise.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        askData();

        refresh.setOnRefreshListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                askData();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    private void askData() {
        final DialogWaiting dialog = DialogWaiting.build(mActivity);
        dialog.show();

        HttpUs.send(Deploy.getDailyPractise(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.getData());

                practises = JSONArray.parseArray(info.getData(), DailyPractise.class);
                if (adapter == null) {
                    adapter = new PractiseAdapter(R.layout.item_practise, practises);
                    rvPractise.setAdapter(adapter);
                } else {
                    adapter.replaceData(practises);
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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DailyPractise dailyPractise = (DailyPractise) adapter.getItem(position);

                //                String title = item.getCourseName();
                //                if (title.equals("诵经典")) {
                //                    ActivityUtils.startActivity(SjdListActivity.class);
                //                } else if (title.equals("习汉字")) {
                //                    ActivityUtils.startActivity(XhzListActivity.class);
                //                } else if (title.equals("练运算")) {
                //                    ActivityUtils.startActivity(LysListActivity.class);
                //                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("dailyPractise", dailyPractise);
                ActivityUtils.startActivity(bundle, SjdListActivity.class);
            }
        });
    }

    @OnClick({R.id.study_tool, R.id.grade_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.study_tool:
                ActivityUtils.startActivity(StudyToolActivity.class);
                break;
            case R.id.grade_report:
                ActivityUtils.startActivity(StatisticsActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
