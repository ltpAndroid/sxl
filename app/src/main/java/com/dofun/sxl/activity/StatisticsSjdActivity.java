package com.dofun.sxl.activity;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

public class StatisticsSjdActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_detail);

        initData();
    }

    private void initData() {
        JSONObject param = new JSONObject();
        param.put("courseId", "10");
        HttpUs.send(Deploy.getAnswerWrongBookDetail(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }
}
