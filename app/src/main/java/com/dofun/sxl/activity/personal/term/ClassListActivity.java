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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassListActivity extends BaseActivity {

    @BindView(R.id.iv_back_school)
    ImageView ivBack;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.lv_school)
    ListView lvClass;

    private ArrayAdapter<String> adapter;
    private List<Integer> classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        tvType.setText("班级");
    }

    private void initData() {
        classId = SPUtils.getListData("classIdList", Integer.class);

        List<String> className = SPUtils.getListData("classNameList", String.class);
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, className);
        lvClass.setAdapter(adapter);
        setListener();

    }

    private void setListener() {
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Id = classId.get(position);
                SPUtils.setString("classId", String.valueOf(Id));

                String content = adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("className", content);
                setResult(1004, intent);
                finish();
            }
        });
    }

    @OnClick(R.id.iv_back_school)
    public void onViewClicked() {
        finish();
    }
}
