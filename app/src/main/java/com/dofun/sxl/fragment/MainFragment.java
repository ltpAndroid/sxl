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

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.LysListActivity;
import com.dofun.sxl.activity.SjdListActivity;
import com.dofun.sxl.activity.StatisticsActivity;
import com.dofun.sxl.activity.StudyToolActivity;
import com.dofun.sxl.activity.XhzListActivity;
import com.dofun.sxl.adapter.PractiseAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.util.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
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
    private List<DailyPractise> practises;

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
        practises = new ArrayList<DailyPractise>();
        practises.add(new DailyPractise("诵经典", "2018-07-07", "张慧敏", "口语评测", "2018-07-09 21:00"));
        practises.add(new DailyPractise("习汉字", "2018-07-07", "张慧敏", "书法练习", "2018-07-09 21:00"));
        practises.add(new DailyPractise("练运算", "2018-07-07", "张慧敏", "心算口算", "2018-07-09 21:00"));

        if (adapter == null) {
            adapter = new PractiseAdapter(R.layout.item_practise, practises);
            rvPractise.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        refresh.setOnRefreshListener(null);
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
        });


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DailyPractise item = (DailyPractise) adapter.getItem(position);
                String title = item.getTitle();
                if (title.equals("诵经典")) {
                    ActivityUtils.startActivity(SjdListActivity.class);
                } else if (title.equals("习汉字")) {
                    ActivityUtils.startActivity(XhzListActivity.class);
                } else if (title.equals("练运算")) {
                    ActivityUtils.startActivity(LysListActivity.class);
                }
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
