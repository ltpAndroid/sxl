package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.ListBeanAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.bean.ListBean;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.view.DialogWaiting;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SjdListActivity extends BaseActivity {

    @BindView(R.id.finish_from_sjd)
    ImageView ivBack;
    @BindView(R.id.rv_sjd_list)
    RecyclerView rvSjdList;
    @BindView(R.id.topicList_title)
    TextView tvTitle;

    private ListBeanAdapter adapter;
    private List<ListBean> beanList = new ArrayList<>();
    private DailyPractise dailyPractise;

    public static String KEY_SJD = "item_num";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjd_list);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSjdList.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_height));
        rvSjdList.addItemDecoration(divider);
    }

    private void initData() {
        final DialogWaiting dialog = DialogWaiting.build(this);
        dialog.show();

        dailyPractise = (DailyPractise) getIntent().getSerializableExtra("dailyPractise");
        String courseName = dailyPractise.getCourseName();
        String type = "";
        switch (courseName) {
            case "诵经典":
                type = "question_types";
                break;
            case "习汉字":
                type = "characters_type";
                break;
            case "练运算":
                type = "operation_type";
                break;
        }
        tvTitle.setText(courseName);

        JSONObject param = new JSONObject();
        param.put("type", type);
        HttpUs.send(Deploy.getTopicType(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());

                beanList = JSONArray.parseArray(info.getData(), ListBean.class);
                if (adapter == null) {
                    adapter = new ListBeanAdapter(R.layout.item_sjd_list, beanList);
                    rvSjdList.setAdapter(adapter);
                } else {
                    adapter.replaceData(beanList);
                }
                dialog.dimiss();

                setListener();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ListBean bean = (ListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                int homeworkId = dailyPractise.getId();
                int fkId = dailyPractise.getCourseId();
                bundle.putInt("homeworkId", homeworkId);
                bundle.putInt("fkId", fkId);
                bundle.putString("type", bean.getType());
                bundle.putInt("position", position);
                //bundle.putInt("kind",bean.getId());
                if (!(bean.getValue().equals("诵读") || bean.getValue().equals("视频"))) {
                    ActivityUtils.startActivity(bundle, SjdDetailActivity.class);
                }
            }
        });
    }

    @OnClick({R.id.finish_from_sjd})
    public void onViewClicked() {
        finish();
    }
}
