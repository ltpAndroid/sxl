package com.dofun.sxl.activity.sjd;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.activity.MainActivity;
import com.dofun.sxl.adapter.TopicListAdapter;
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

public class SjdCheckActivity extends BaseActivity {

    @BindView(R.id.iv_back_final)
    ImageView ivBack;
    @BindView(R.id.rv_tk)
    RecyclerView rvTk;
    @BindView(R.id.tv_lx)
    TextView tvLx;
    @BindView(R.id.iv_isRight1)
    ImageView ivIsRight1;
    @BindView(R.id.ll_lx)
    LinearLayout llLx;
    @BindView(R.id.tv_ps)
    TextView tvPs;
    @BindView(R.id.iv_isRight2)
    ImageView ivIsRight2;
    @BindView(R.id.ll_ps)
    LinearLayout llPs;
    @BindView(R.id.rv_xz)
    RecyclerView rvXz;
    @BindView(R.id.rv_pd)
    RecyclerView rvPd;
    @BindView(R.id.tv_sd)
    TextView tvSd;
    @BindView(R.id.iv_isRight3)
    ImageView ivIsRight3;
    @BindView(R.id.ll_sd)
    LinearLayout llSd;
    @BindView(R.id.btn_do_again)
    Button btnDoAgain;
    @BindView(R.id.btn_do_wrong)
    Button btnDoWrong;

    private List<TopicDetail> topicDetailList = new ArrayList<>();
    private List<TopicDetail> tkList = new ArrayList<>();
    private List<TopicDetail> xzList = new ArrayList<>();
    private List<TopicDetail> pdList = new ArrayList<>();
    private TopicListAdapter tkAdapter;
    private TopicListAdapter xzAdapter;
    private TopicListAdapter pdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjd_check);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager tkManager = new LinearLayoutManager(this);
        tkManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTk.setLayoutManager(tkManager);

        LinearLayoutManager xzManager = new LinearLayoutManager(this);
        xzManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvXz.setLayoutManager(xzManager);

        LinearLayoutManager pdManager = new LinearLayoutManager(this);
        pdManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPd.setLayoutManager(pdManager);
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
                topicDetailList = JSONArray.parseArray(info.getData(), TopicDetail.class);
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
                case 101:
                    tkList.add(topic);
                    break;
                case 102:
                    tvLx.setText("1、" + topic.getDetail());
                    List<Answer> lxAnswer = (List<Answer>) AnswerConstants.sjdMap.get(102);
                    String isRight1 = lxAnswer.get(0).getIsRight();
                    if (isRight1.equals("1")) {
                        ivIsRight1.setImageResource(R.drawable.right);
                    } else {
                        ivIsRight1.setImageResource(R.drawable.wrong);
                    }
                    break;
                case 103:
                    tvPs.setText("1、" + topic.getDetail());
                    List<Answer> psAnswer = (List<Answer>) AnswerConstants.sjdMap.get(103);
                    String isRight2 = psAnswer.get(0).getIsRight();
                    if (isRight2.equals("1")) {
                        ivIsRight2.setImageResource(R.drawable.right);
                    } else {
                        ivIsRight2.setImageResource(R.drawable.wrong);
                    }
                    break;
                case 104:
                    xzList.add(topic);
                    break;
                case 105:
                    pdList.add(topic);
                    break;
                case 106:
                    tvSd.setText("1、" + topic.getDetail());
                    List<Answer> sdAnswer = (List<Answer>) AnswerConstants.sjdMap.get(106);
                    String isRight3 = sdAnswer.get(0).getIsRight();
                    if (isRight3.equals("1")) {
                        ivIsRight3.setImageResource(R.drawable.right);
                    } else {
                        ivIsRight3.setImageResource(R.drawable.wrong);
                    }
                    break;
            }
        }
        judgeKind(kindList);
        judgeIsRight();
    }

    private void judgeIsRight() {
        List<Answer> tkAnswer = (List<Answer>) AnswerConstants.sjdMap.get(101);
        List<TopicRightBean> tkBeans = new ArrayList<>();
        for (int i = 0; i < tkList.size(); i++) {
            TopicDetail detail = tkList.get(i);
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
        tkAdapter = new TopicListAdapter(R.layout.item_is_right, tkBeans);
        rvTk.setAdapter(tkAdapter);


        List<Answer> xzAnswer = (List<Answer>) AnswerConstants.sjdMap.get(104);
        List<TopicRightBean> xzBeans = new ArrayList<>();
        for (int i = 0; i < xzList.size(); i++) {
            TopicDetail detail = xzList.get(i);
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
        xzAdapter = new TopicListAdapter(R.layout.item_is_right, xzBeans);
        rvXz.setAdapter(xzAdapter);


        List<Answer> pdAnswer = (List<Answer>) AnswerConstants.sjdMap.get(105);
        List<TopicRightBean> pdBeans = new ArrayList<>();
        for (int i = 0; i < pdList.size(); i++) {
            TopicDetail detail = pdList.get(i);
            TopicRightBean bean = new TopicRightBean();
            bean.setDetail(detail.getDetail());
            String isRight = pdAnswer.get(i).getIsRight();
            if (isRight.equals("1")) {
                bean.setRight(true);
            } else {
                bean.setRight(false);
            }
            pdBeans.add(bean);
        }
        pdAdapter = new TopicListAdapter(R.layout.item_is_right, pdBeans);
        rvPd.setAdapter(pdAdapter);
    }


    private void judgeKind(List<Integer> kinds) {
        if (!kinds.contains(101)) {
            rvTk.setVisibility(View.GONE);
        }
        if (!kinds.contains(102)) {
            llLx.setVisibility(View.GONE);
        }
        if (!kinds.contains(103)) {
            llPs.setVisibility(View.GONE);
        }
        if (!kinds.contains(104)) {
            rvXz.setVisibility(View.GONE);
        }
        if (!kinds.contains(105)) {
            rvPd.setVisibility(View.GONE);
        }
        if (!kinds.contains(106)) {
            llSd.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.iv_back_final, R.id.btn_do_again, R.id.btn_do_wrong})
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
        AnswerConstants.sjdMap.clear();
    }
}
