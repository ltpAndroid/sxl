package com.dofun.sxl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.PersonRewardActivity;
import com.dofun.sxl.activity.PersonalLevelActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MeFragment extends BaseFragment {

    @BindView(R.id.personal_reward)
    TextView personalReward;
    Unbinder unbinder;
    @BindView(R.id.personal_level)
    TextView personalLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        // setTitleStr("个人中心",true);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.personal_reward, R.id.personal_level})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_reward:
                ActivityUtils.startActivity(PersonRewardActivity.class);
                break;
            case R.id.personal_level:
                ActivityUtils.startActivity(PersonalLevelActivity.class);
                break;
        }
    }
}
