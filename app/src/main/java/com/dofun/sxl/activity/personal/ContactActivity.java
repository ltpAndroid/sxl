package com.dofun.sxl.activity.personal;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactActivity extends BaseActivity {

    @BindView(R.id.tv_back_contact)
    TextView tvBack;
    @BindView(R.id.btn_wx)
    ImageView btnWx;
    @BindView(R.id.btn_phone)
    ImageView btnPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        //checkPer(Permission.READ_CONTACTS);
    }

    @OnClick({R.id.tv_back_contact, R.id.btn_wx, R.id.btn_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_contact:
                finish();
                break;
            case R.id.btn_wx:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("wx", "敦锋武汉科技有限公司");
                cm.setPrimaryClip(data);
                showTip(R.string.clip_wx);
                break;
            case R.id.btn_phone:
                showMyDialog(R.string.contact_phone, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PhoneUtils.dial("027-85418630");
                    }
                });
                break;
        }
    }
}
