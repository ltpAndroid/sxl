package com.dofun.sxl.activity.personal.term;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.SchoolListBean;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SchoolListActivity extends BaseActivity {

    @BindView(R.id.iv_back_school)
    ImageView ivBack;
    @BindView(R.id.lv_school)
    ListView lvSchool;
    @BindView(R.id.tv_type)
    TextView tvType;

    private ArrayAdapter<String> adapter;
    private List<SchoolListBean> schoolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        tvType.setText("学校");
    }

    private void initData() {
        HttpUs.send(Deploy.querySchoolClass(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                JSONObject object = JSONObject.parseObject(info.getData());
                schoolList = JSONArray.parseArray(object.getString("schoolClassList"), SchoolListBean.class);

                List<SchoolListBean.ClassListBean> classList = new ArrayList<>();
                for (SchoolListBean school :
                        schoolList) {
                    classList.addAll(school.getClassList());
                }

                List<String> className = new ArrayList<>();
                for (SchoolListBean.ClassListBean classBean :
                        classList) {
                    className.add(classBean.getClassName());
                }
                SPUtils.putListData("classNameList", className);

                List<Integer> classId = new ArrayList<>();
                for (SchoolListBean.ClassListBean classBean :
                        classList) {
                    classId.add(classBean.getId());
                }
                SPUtils.putListData("classIdList", classId);

                List<String> schoolName = new ArrayList<>();
                for (SchoolListBean school :
                        schoolList) {
                    schoolName.add(school.getSchoolName());
                }
                adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, schoolName);
                lvSchool.setAdapter(adapter);
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
        lvSchool.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int schoolId = schoolList.get(position).getSchoolId();
                SPUtils.setString("schoolId", String.valueOf(schoolId));

                String content = adapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("schoolName", content);
                setResult(1001, intent);
                finish();
            }
        });
    }

    @OnClick(R.id.iv_back_school)
    public void onViewClicked() {
        finish();
    }
}
