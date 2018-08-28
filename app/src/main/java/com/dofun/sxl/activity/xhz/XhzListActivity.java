package com.dofun.sxl.activity.xhz;

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
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.adapter.ListBeanAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.dofun.sxl.bean.ListBean;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XhzListActivity extends BaseActivity {


    @BindView(R.id.topicList_title)
    TextView tvTitle;
    @BindView(R.id.finish_from_sjd)
    ImageView ivBack;
    @BindView(R.id.rv_sjd_list)
    RecyclerView rvXhzList;

    private ListBeanAdapter adapter;
    private List<ListBean> beanList = new ArrayList<>();
    private DailyPractise dailyPractise;

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
        tvTitle.setText("习汉字");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvXhzList.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_height));
        rvXhzList.addItemDecoration(divider);

    }

    private void initData() {
        dailyPractise = (DailyPractise) getIntent().getSerializableExtra("dailyPractise");

        JSONObject param = new JSONObject();
        param.put("type", "characters_type");
        HttpUs.send(Deploy.getTopicType(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());

                beanList = JSONArray.parseArray(info.getData(), ListBean.class);
                if (adapter == null) {
                    adapter = new ListBeanAdapter(R.layout.item_sjd_list, beanList);
                    rvXhzList.setAdapter(adapter);
                } else {
                    adapter.replaceData(beanList);
                }

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
                Bundle bundle = new Bundle();
                int homeworkId = dailyPractise.getId();
                int fkId = dailyPractise.getCourseId();
                bundle.putInt("homeworkId", homeworkId);
                bundle.putInt("fkId", fkId);
                switch (position) {
                    case 0:
                        ActivityUtils.startActivity(bundle, FillWordActivity.class);
                        break;
                    case 1:
                        ActivityUtils.startActivity(bundle, WriteWordActivity.class);
                        break;

                }
            }
        });
    }

    @OnClick(R.id.finish_from_sjd)
    public void onViewClicked() {
        finish();
    }
}
