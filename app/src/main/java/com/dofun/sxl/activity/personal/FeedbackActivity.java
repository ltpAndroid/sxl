package com.dofun.sxl.activity.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.tv_back_feedback)
    TextView tvBack;
    @BindView(R.id.tv_send_feedback)
    TextView tvSend;
    @BindView(R.id.feedback_content)
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back_feedback, R.id.tv_send_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_feedback:
                finish();
                break;
            case R.id.tv_send_feedback:
                String content = etContent.getText().toString();
                if (StringUtils.isEmpty(content)) {
                    showTip(R.string.empty_content);
                    return;
                }

                JSONObject params = new JSONObject();
                params.put("content", content);
                HttpUs.send(Deploy.sendFeedback(), params, new HttpUs.CallBackImp() {
                    @Override
                    public void onSuccess(ResInfo info) {
                        HintDiaUtils.createDialog(mContext).showSucceedDialog("发送成功");
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }

                    @Override
                    public void onFailure(ResInfo info) {
                        showTip(info.getMsg());
                    }
                }, mContext, "正在发送");
                break;
        }
    }
}
