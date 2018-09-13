package com.dofun.sxl.activity.personal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.MyApplication;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.activity.personal.term.PerfectInfoActivity;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.CacheUtil;
import com.dofun.sxl.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_back_setting)
    TextView tvBack;
    @BindView(R.id.change_term)
    LinearLayout changeTerm;
    @BindView(R.id.net_setting)
    LinearLayout netSetting;
    @BindView(R.id.clear_cache)
    LinearLayout clearCache;
    @BindView(R.id.btn_quit)
    Button btnQuit;
    @BindView(R.id.tv_cache)
    TextView tvCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int cacheSize = (int) CacheUtil.getInstance().getCacheSize();
                tvCache.setText(cacheSize + " M");
            }
        }).start();
    }

    @OnClick({R.id.tv_back_setting, R.id.change_term, R.id.net_setting, R.id.clear_cache, R.id.btn_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_setting:
                finish();
                break;
            case R.id.change_term:
                ActivityUtils.startActivity(PerfectInfoActivity.class);
                break;
            case R.id.net_setting:
                ActivityUtils.startActivity(ChangeNetActivity.class);
                break;
            case R.id.clear_cache:
                showMyDialog(R.string.clean_cache, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CleanUtils.cleanInternalCache();
                        tvCache.setText("0 M");
                        showTip("缓存已清理");
                    }
                });
                break;
            case R.id.btn_quit:
                showMyDialog(R.string.quit_app, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HttpUs.send(Deploy.getLogout(), null, new HttpUs.CallBackImp() {
                            @Override
                            public void onSuccess(ResInfo info) {
                                LogUtils.i(info.toString());

                                SPUtils.setString(SPUtils.UserName, "");
                                SPUtils.setString(SPUtils.UserPwd, "");
                                MyApplication.exitApp();
                                MyApplication.toLogin();
                            }

                            @Override
                            public void onFailure(ResInfo info) {
                                LogUtils.i(info.toString());
                                showTip(info.getMsg());
                            }
                        }, mContext, "正在退出");
                    }
                });
                break;
        }
    }
}
