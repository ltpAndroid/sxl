package com.dofun.sxl.activity.personal.term;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermListActivity extends BaseActivity {

    @BindView(R.id.iv_back_school)
    ImageView ivBack;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.lv_school)
    ListView lvTerm;

    private ArrayAdapter<String> adapter;
    private String[] term = {"上学期", "下学期"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        tvType.setText("学期");
    }

    private void initData() {
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, term);
        lvTerm.setAdapter(adapter);
        setListener();
    }

    private void setListener() {
        lvTerm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int term = position + 1;
                SPUtils.setString("term", String.valueOf(term));

                String content = adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("termName", content);
                setResult(1003, intent);
                finish();
            }
        });
    }

    @OnClick(R.id.iv_back_school)
    public void onViewClicked() {
        finish();
    }
}
