package com.dofun.sxl.activity.personal;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.adapter.StrAdapter;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LysKsActivity extends BaseActivity {

    @BindView(R.id.tv_back_ks)
    TextView tvBack;
    @BindView(R.id.current_num)
    TextView tvCurrent;
    @BindView(R.id.total_num)
    TextView tvTotal;
    @BindView(R.id.topic_detail)
    TextView tvDetail;
    @BindView(R.id.topic_result)
    TextView tvResult;
    @BindView(R.id.rv_calculator)
    RecyclerView rvCalculator;
    @BindView(R.id.btn_next)
    Button btnNext;

    StrAdapter adapter;
    List<String> str = new ArrayList<>();
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lys_ks);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
        initData();
    }

    private void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        rvCalculator.setLayoutManager(manager);
    }

    private void initData() {
        str.add("1");
        str.add("2");
        str.add("3");
        str.add("4");
        str.add("5");
        str.add("6");
        str.add("7");
        str.add("8");
        str.add("9");
        str.add("0");
        str.add(".");
        str.add("删除");
        adapter = new StrAdapter(R.layout.item_keyboard, str);
        rvCalculator.setAdapter(adapter);
        setListener();

        int homeworkId = getIntent().getIntExtra("homeworkId", 0);
        int fkId = getIntent().getIntExtra("fkId", 0);

        JSONObject param = new JSONObject();
        param.put("homeworkId", homeworkId);
        param.put("fkId", fkId);
        param.put("kind", 121);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
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

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 11) {
                    if (result.length() == 0) {
                        showTip("结果已删除");
                        return;
                    }
                    result = result.substring(0, result.length() - 1);
                    tvResult.setText(result);
                } else {
                    String num = (String) adapter.getItem(position);
                    if (result.length() < 5) {
                        result += num;
                        tvResult.setText(result);
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_back_ks, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_ks:
                finish();
                break;
            case R.id.btn_next:
                int current = Integer.parseInt(tvCurrent.getText().toString());
                if (current < 10) {
                    tvCurrent.setText(String.valueOf(++current));
                } else {
                    showTip("已到最后一题");
                    return;
                }
                break;
        }
    }
}
