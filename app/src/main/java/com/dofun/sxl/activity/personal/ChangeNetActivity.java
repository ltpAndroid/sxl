package com.dofun.sxl.activity.personal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeNetActivity extends BaseActivity {

    @BindView(R.id.tv_back_net)
    TextView tvBack;
    @BindView(R.id.s_download)
    Switch sDownload;
    @BindView(R.id.s_play)
    Switch sPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_net);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
        setListener();
    }

    private void initView() {
        sDownload.setChecked(SPUtils.getBoolean(SPUtils.sDownload));
        sPlay.setChecked(SPUtils.getBoolean(SPUtils.sPlay));
    }

    private void setListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sDownload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showTip("On");
                    showMyDialog(R.string.download_on, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sDownload.setChecked(false);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SPUtils.setBoolean(SPUtils.sDownload, true);
                        }
                    });
                } else {
                    showTip("Off");
                    SPUtils.setBoolean(SPUtils.sDownload, false);
                }
            }
        });

        sPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showTip("On");
                    showMyDialog(R.string.play_on, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            sPlay.setChecked(false);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SPUtils.setBoolean(SPUtils.sPlay, true);
                        }
                    });
                } else {
                    showTip("Off");
                    SPUtils.setBoolean(SPUtils.sPlay, false);
                }
            }
        });
    }

}
