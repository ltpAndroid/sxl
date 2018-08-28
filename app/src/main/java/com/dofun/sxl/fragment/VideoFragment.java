package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.VideoAdapter;
import com.dofun.sxl.bean.VideoBean;
import com.dofun.sxl.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends BaseFragment {


    @BindView(R.id.banner_video)
    Banner bannerVideo;
    @BindView(R.id.rv_video)
    RecyclerView rvVideo;
    Unbinder unbinder;

    private VideoAdapter adapter;
    private List<VideoBean> videoList = new ArrayList<>();

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideo.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mActivity, R.drawable.divider_height));
        rvVideo.addItemDecoration(divider);
    }

    private void initData() {
        //设置图片加载器
        bannerVideo.setImageLoader(new GlideImageLoader());
        bannerVideo.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片集合
        List<Integer> images = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            images.add(R.drawable.banner_lesson);
        }
        bannerVideo.setImages(images);
        //轮播间隔时间
        bannerVideo.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        bannerVideo.start();


        videoList.add(new VideoBean("00:13:59", "07月10日语文作业", "语文老师", "培优组、进步组", "2018-07-09 12:16"));
        videoList.add(new VideoBean("00:15:59", "07月11日语文作业", "语文老师", "培优组、进步组", "2018-07-09 12:16"));
        videoList.add(new VideoBean("00:18:30", "07月11日语文作业", "语文老师", "培优组、进步组", "2018-07-09 12:16"));
        videoList.add(new VideoBean("00:22:20", "07月12日语文作业", "语文老师", "培优组、进步组", "2018-07-09 12:16"));

        if (adapter == null) {
            adapter = new VideoAdapter(R.layout.item_video_list, videoList);
            rvVideo.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
