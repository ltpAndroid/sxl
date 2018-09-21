package com.dofun.sxl.activity.lys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.activity.MainActivity;
import com.dofun.sxl.adapter.TopicImgAdapter;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.bean.TopicRightBean;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LysCheckActivity extends BaseActivity {

    @BindView(R.id.rv_ks)
    RecyclerView rvKs;
    @BindView(R.id.rv_js)
    RecyclerView rvJs;
    @BindView(R.id.rv_tk)
    RecyclerView rvTk;
    @BindView(R.id.rv_xz)
    RecyclerView rvXz;
    @BindView(R.id.btn_do_again)
    Button btnDoAgain;
    @BindView(R.id.btn_do_wrong)
    Button btnDoWrong;

    private List<TopicDetail> ksList = new ArrayList<>();
    private List<TopicDetail> jsList = new ArrayList<>();
    private List<TopicDetail> tkList = new ArrayList<>();
    private List<TopicDetail> xzList = new ArrayList<>();

    private TopicImgAdapter ksAdapter;
    private TopicImgAdapter jsAdapter;
    private TopicImgAdapter tkAdapter;
    private TopicImgAdapter xzAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lys_check);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager ksManager = new LinearLayoutManager(this);
        ksManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvKs.setLayoutManager(ksManager);

        LinearLayoutManager jsManager = new LinearLayoutManager(this);
        jsManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJs.setLayoutManager(jsManager);

        LinearLayoutManager tkManager = new LinearLayoutManager(this);
        tkManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTk.setLayoutManager(tkManager);

        LinearLayoutManager xzManager = new LinearLayoutManager(this);
        xzManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvXz.setLayoutManager(xzManager);
    }

    private void initData() {
        int homeworkId = getIntent().getIntExtra("homeworkId", 0);
        int fkId = getIntent().getIntExtra("fkId", 0);
        JSONObject param = new JSONObject();
        param.put("homeworkId", homeworkId);
        param.put("fkId", fkId);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                List<TopicDetail> topicDetailList = JSONArray.parseArray(info.getData(), TopicDetail.class);
                bindView(topicDetailList);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void bindView(List<TopicDetail> topicDetails) {
        List<Integer> kindList = new ArrayList<>();
        for (TopicDetail topic :
                topicDetails) {
            kindList.add(topic.getKind());

            switch (topic.getKind()) {
                case 121:
                    ksList.add(topic);
                    break;
                case 122:
                    jsList.add(topic);
                    break;
                case 123:
                    tkList.add(topic);
                    break;
                case 124:
                    xzList.add(topic);
                    break;
            }
        }
        judgeKind(kindList);
        judgeIsRight();
    }

    private void judgeIsRight() {
        List<Answer> ksAnswer = (List<Answer>) AnswerConstants.lysMap.get(121);
        List<TopicRightBean> ksBeans = new ArrayList<>();
        for (int i = 0; i < ksList.size(); i++) {
            TopicDetail detail = ksList.get(i);
            TopicRightBean bean = new TopicRightBean();
            bean.setDetail(detail.getDetail());
            String isRight = ksAnswer.get(i).getIsRight();
            if (isRight.equals("1")) {
                bean.setRight(true);
            } else {
                bean.setRight(false);
            }
            ksBeans.add(bean);
        }
        ksAdapter = new TopicImgAdapter(R.layout.item_lys_right, ksBeans);
        rvKs.setAdapter(ksAdapter);


        List<Answer> jsAnswer = (List<Answer>) AnswerConstants.lysMap.get(122);
        List<TopicRightBean> jsBeans = new ArrayList<>();
        for (int i = 0; i < jsList.size(); i++) {
            TopicDetail detail = jsList.get(i);
            TopicRightBean bean = new TopicRightBean();
            bean.setDetail(detail.getDetail());
            String isRight = jsAnswer.get(i).getIsRight();
            if (isRight.equals("1")) {
                bean.setRight(true);
            } else {
                bean.setRight(false);
            }
            jsBeans.add(bean);
        }
        jsAdapter = new TopicImgAdapter(R.layout.item_lys_right, jsBeans);
        rvJs.setAdapter(jsAdapter);


        List<Answer> tkAnswer = (List<Answer>) AnswerConstants.lysMap.get(123);
        List<TopicRightBean> tkBeans = new ArrayList<>();
        for (int i = 0; i < jsList.size(); i++) {
            TopicDetail detail = jsList.get(i);
            TopicRightBean bean = new TopicRightBean();
            bean.setDetail(detail.getDetail());
            String isRight = tkAnswer.get(i).getIsRight();
            if (isRight.equals("1")) {
                bean.setRight(true);
            } else {
                bean.setRight(false);
            }
            tkBeans.add(bean);
        }
        tkAdapter = new TopicImgAdapter(R.layout.item_lys_right, tkBeans);
        rvTk.setAdapter(tkAdapter);


        List<Answer> xzAnswer = (List<Answer>) AnswerConstants.lysMap.get(124);
        List<TopicRightBean> xzBeans = new ArrayList<>();
        for (int i = 0; i < jsList.size(); i++) {
            TopicDetail detail = jsList.get(i);
            TopicRightBean bean = new TopicRightBean();
            bean.setDetail(detail.getDetail());
            String isRight = xzAnswer.get(i).getIsRight();
            if (isRight.equals("1")) {
                bean.setRight(true);
            } else {
                bean.setRight(false);
            }
            xzBeans.add(bean);
        }
        xzAdapter = new TopicImgAdapter(R.layout.item_lys_right, xzBeans);
        rvXz.setAdapter(xzAdapter);
    }

    private void judgeKind(List<Integer> kinds) {
        if (!kinds.contains(121)) {
            rvKs.setVisibility(View.GONE);
        }
        if (!kinds.contains(122)) {
            rvJs.setVisibility(View.GONE);
        }
        if (!kinds.contains(123)) {
            rvTk.setVisibility(View.GONE);
        }
        if (!kinds.contains(124)) {
            rvXz.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_do_again, R.id.btn_do_wrong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_do_again:
                finish();
                break;
            case R.id.btn_do_wrong:
                MainActivity.navToOrder(this, 2);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AnswerConstants.lysMap.clear();
    }
}
