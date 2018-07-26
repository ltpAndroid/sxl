package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dofun.sxl.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LessonFragment extends BaseFragment {
    @BindView(R.id.tv_title_wk)
    TextView tvVideo;
    @BindView(R.id.tv_title_kzkt)
    TextView tvLive;
    @BindView(R.id.lesson_container)
    FrameLayout lessonContainer;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.tv_title_wk, R.id.tv_title_kzkt})
    public void onViewClicked(View view) {
        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.tv_title_wk:
                tvVideo.setEnabled(false);
                tvLive.setEnabled(true);
                tvVideo.setTextColor(setColor(R.color.main_color));
                tvLive.setTextColor(setColor(R.color.md_black_1000));

                VideoFragment videoFragment = new VideoFragment();
                ft.replace(R.id.lesson_container, videoFragment).commit();
                break;
            case R.id.tv_title_kzkt:
                tvLive.setEnabled(false);
                tvVideo.setEnabled(true);
                tvLive.setTextColor(setColor(R.color.main_color));
                tvVideo.setTextColor(setColor(R.color.md_black_1000));

                LiveFragment liveFragment = new LiveFragment();
                ft.replace(R.id.lesson_container, liveFragment).commit();
                break;
        }
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        initFragment();
    }

    private void initFragment() {
        FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
        VideoFragment videoFragment = new VideoFragment();
        ft.replace(R.id.lesson_container, videoFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
