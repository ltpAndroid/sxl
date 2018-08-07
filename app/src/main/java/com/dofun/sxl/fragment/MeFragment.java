package com.dofun.sxl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.personal.ContactActivity;
import com.dofun.sxl.activity.personal.FeedbackActivity;
import com.dofun.sxl.activity.personal.JoinActivity;
import com.dofun.sxl.activity.personal.PersonRewardActivity;
import com.dofun.sxl.activity.personal.PersonalLevelActivity;
import com.dofun.sxl.activity.personal.SettingActivity;
import com.dofun.sxl.activity.personal.UserInfoActivity;

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
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_join)
    TextView tvJoin;
    @BindView(R.id.iv_header)
    ImageView ivHeader;

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

    @OnClick({R.id.personal_reward, R.id.personal_level, R.id.tv_feedback,
            R.id.tv_setting, R.id.tv_contact, R.id.tv_join, R.id.iv_header})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_reward:
                ActivityUtils.startActivity(PersonRewardActivity.class);
                break;
            case R.id.personal_level:
                ActivityUtils.startActivity(PersonalLevelActivity.class);
                break;
            case R.id.tv_feedback:
                ActivityUtils.startActivity(FeedbackActivity.class);
                break;
            case R.id.tv_setting:
                ActivityUtils.startActivity(SettingActivity.class);
                break;
            case R.id.tv_contact:
                ActivityUtils.startActivity(ContactActivity.class);
                break;
            case R.id.tv_join:
                ActivityUtils.startActivity(JoinActivity.class);
                break;
            case R.id.iv_header:
                ActivityUtils.startActivity(UserInfoActivity.class);
                break;
        }
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        getUserUI();
    }

    private void getUserUI() {
        showTip("233");
    }
}
