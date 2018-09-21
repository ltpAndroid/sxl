package com.dofun.sxl.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.DailyPractiseActivity;
import com.dofun.sxl.activity.InteractActivity;
import com.dofun.sxl.activity.NoticeListActivity;
import com.dofun.sxl.activity.StatisticsActivity;
import com.dofun.sxl.activity.StudyToolActivity;
import com.dofun.sxl.bean.BroadcastImg;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.GlideImageLoader;
import com.dofun.sxl.util.HintDiaUtils;
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
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    Unbinder unbinder;
    @BindView(R.id.ll_sjd)
    LinearLayout llSjd;
    @BindView(R.id.ll_xhz)
    LinearLayout llXhz;
    @BindView(R.id.ll_lys)
    LinearLayout llLys;
    @BindView(R.id.ll_rush)
    LinearLayout llRush;
    @BindView(R.id.ll_report)
    LinearLayout llReport;
    @BindView(R.id.ll_interact)
    LinearLayout llInteract;
    @BindView(R.id.ll_tool)
    LinearLayout llTool;

    private List<DailyPractise> practises = new ArrayList<>();
    private ArrayList<DailyPractise> sjdPractises = new ArrayList<>();
    private ArrayList<DailyPractise> xhzPractises = new ArrayList<>();
    private ArrayList<DailyPractise> lysPractises = new ArrayList<>();

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

        JSONObject param = new JSONObject();
        param.put("type", 1);
        param.put("status", 1);
        HttpUs.send(Deploy.queryBroadcastImages(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                List<BroadcastImg> imgList = JSONArray.parseArray(info.getData(), BroadcastImg.class);
                setBanner(imgList);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });

    }

    private void setBanner(List<BroadcastImg> imgList) {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片集合
        final List<String> imgPath = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            BroadcastImg broadcastImg = imgList.get(i);
            imgPath.add(Deploy.ImgURL + broadcastImg.getImagePath());
        }
        banner.setImages(imgPath);
        //轮播间隔时间
        banner.setDelayTime(3000);
        //banner点击监听
        //        banner.setOnBannerListener(new OnBannerListener() {
        //            @Override
        //            public void OnBannerClick(int position) {
        //                ActivityUtils.startActivity(WebActivity.class);
        //            }
        //        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initData() {
        askData();

    }

    private void askData() {
        //        final DialogWaiting dialog = DialogWaiting.build(mActivity);
        //        dialog.show();

        HttpUs.send(Deploy.getDailyPractise(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.getData());

                practises = JSONArray.parseArray(info.getData(), DailyPractise.class);
                bindData(practises);
            }


            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.getMsg());
                showTip(info.getMsg());
            }
        });
    }

    private void bindData(List<DailyPractise> practises) {
        for (DailyPractise daily :
                practises) {
            switch (daily.getCourseName()) {
                case "诵经典":
                    sjdPractises.add(daily);
                    break;
                case "习汉字":
                    xhzPractises.add(daily);
                    break;
                default:
                    lysPractises.add(daily);
                    break;
            }
        }
        bindView();
    }

    private void bindView() {
        if (sjdPractises.size() > 0) {
            llSjd.setBackgroundResource(R.drawable.sjd_new);
        } else {
            llSjd.setBackgroundResource(R.drawable.sjd_null);
        }

        if (xhzPractises.size() > 0) {
            llXhz.setBackgroundResource(R.drawable.xhz_new);
        } else {
            llXhz.setBackgroundResource(R.drawable.xhz_null);
        }

        if (lysPractises.size() > 0) {
            llLys.setBackgroundResource(R.drawable.lys_new);
        } else {
            llLys.setBackgroundResource(R.drawable.lys_null);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.iv_notice, R.id.ll_sjd, R.id.ll_xhz, R.id.ll_lys, R.id.ll_rush, R.id.ll_report, R.id.ll_tool, R.id.ll_interact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_notice:
                ActivityUtils.startActivity(NoticeListActivity.class);
                break;
            case R.id.ll_sjd:
                if (sjdPractises.size() == 0) {
                    showEmptyToast();
                    return;
                }
                Bundle bundleS = new Bundle();
                bundleS.putSerializable("practises", sjdPractises);
                ActivityUtils.startActivity(bundleS, DailyPractiseActivity.class);
                break;
            case R.id.ll_xhz:
                if (xhzPractises.size() == 0) {
                    showEmptyToast();
                    return;
                }
                Bundle bundleX = new Bundle();
                bundleX.putSerializable("practises", xhzPractises);
                ActivityUtils.startActivity(bundleX, DailyPractiseActivity.class);
                break;
            case R.id.ll_lys:
                if (lysPractises.size() == 0) {
                    showEmptyToast();
                    return;
                }
                Bundle bundleL = new Bundle();
                bundleL.putSerializable("practises", lysPractises);
                ActivityUtils.startActivity(bundleL, DailyPractiseActivity.class);
                break;
            case R.id.ll_report:
                ActivityUtils.startActivity(StatisticsActivity.class);
                break;
            case R.id.ll_tool:
                ActivityUtils.startActivity(StudyToolActivity.class);
                break;
            case R.id.ll_interact:
                ActivityUtils.startActivity(InteractActivity.class);
                break;
            case R.id.ll_rush:
                break;
        }
    }

    private void showEmptyToast() {
        final HintDiaUtils dialog = HintDiaUtils.createDialog(mActivity);
        dialog.showDialog("今天暂无\n 此习题", R.drawable.warn);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }
}
