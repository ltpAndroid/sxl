package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.fragment.AnnounceFragment;
import com.dofun.sxl.fragment.MessageFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InteractActivity extends BaseActivity {

    @BindView(R.id.iv_back_interact)
    ImageView ivBack;
    @BindView(R.id.tv_announce)
    TextView tvAnnounce;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private AnnounceFragment announceFragment;
    private MessageFragment messageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_list);
        ButterKnife.bind(this);

        initView();
        tvAnnounce.setSelected(true);
    }

    private void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        announceFragment = new AnnounceFragment();
        ft.add(R.id.fragment_container, announceFragment).commit();
    }


    @OnClick({R.id.iv_back_interact, R.id.tv_announce, R.id.tv_msg})
    public void onViewClicked(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideAllFragment(ft);
        switch (view.getId()) {
            case R.id.iv_back_interact:
                finish();
                break;
            case R.id.tv_announce:
                tvAnnounce.setTextColor(setColor(R.color.main_color));
                tvMsg.setTextColor(setColor(R.color.white));
                tvAnnounce.setSelected(true);
                tvMsg.setSelected(false);

                if (announceFragment == null) {
                    announceFragment = new AnnounceFragment();
                    ft.add(R.id.fragment_container, announceFragment);
                } else {
                    ft.show(announceFragment);
                }
                break;
            case R.id.tv_msg:
                tvAnnounce.setTextColor(setColor(R.color.white));
                tvMsg.setTextColor(setColor(R.color.main_color));
                tvAnnounce.setSelected(false);
                tvMsg.setSelected(true);

                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    ft.add(R.id.fragment_container, messageFragment);
                } else {
                    ft.show(messageFragment);
                }
                break;
        }
        ft.commit();
    }

    private void hideAllFragment(FragmentTransaction ft) {
        if (announceFragment != null) {
            ft.hide(announceFragment);
        }
        if (messageFragment != null) {
            ft.hide(messageFragment);
        }
    }
}
