package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.VideoDetailActivity;
import com.dofun.sxl.adapter.VideoAdapter;
import com.dofun.sxl.bean.VideoBean;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.GlideImageLoader;
import com.dofun.sxl.util.LogUtil;
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
    @BindView(R.id.refresh_video)
    SmartRefreshLayout refreshVideo;

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

        refreshVideo.setRefreshHeader(new ClassicsHeader(mActivity))
                .setRefreshFooter(new ClassicsFooter(mActivity));


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
    }

    private void initData() {

        HttpUs.send(Deploy.getItemPage(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtil.i("过长信息", info.getData());
                videoList = JSONArray.parseArray(info.getData(), VideoBean.class);
                if (videoList.size() == 0) {
                    showTip("暂无微课视频");
                    return;
                }
                if (adapter == null) {
                    adapter = new VideoAdapter(R.layout.item_video_list, videoList);
                    rvVideo.setAdapter(adapter);
                    setListener();
                } else {
                    adapter.replaceData(videoList);
                }
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
                VideoBean videoBean = (VideoBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("videoBean", videoBean);
                ActivityUtils.startActivity(bundle, VideoDetailActivity.class);
            }
        });


        refreshVideo.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
