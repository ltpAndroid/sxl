package com.dofun.sxl.activity.personal.term;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PerfectInfoActivity extends BaseActivity {

    @BindView(R.id.iv_back_perfect)
    ImageView ivBack;
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.ll_school)
    LinearLayout llSchool;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.ll_grade)
    LinearLayout llGrade;
    @BindView(R.id.tv_term)
    TextView tvTerm;
    @BindView(R.id.ll_term)
    LinearLayout llTerm;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.ll_class)
    LinearLayout llClass;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    private boolean isMain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        isMain = getIntent().getBooleanExtra("main", false);
        if (isMain) {
            ivBack.setVisibility(View.GONE);
        } else {
            ivBack.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_back_perfect, R.id.ll_school, R.id.ll_grade, R.id.ll_term, R.id.ll_class, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_perfect:
                finish();
                break;
            case R.id.ll_school:
                Intent school = new Intent(this, SchoolListActivity.class);
                startActivityForResult(school, 101);
                break;
            case R.id.ll_grade:
                if (tvSchool.getText().toString().isEmpty()) {
                    showTip("请先选择学校");
                    return;
                }

                Intent grade = new Intent(this, GradeListActivity.class);
                startActivityForResult(grade, 102);
                break;
            case R.id.ll_term:
                if (tvGrade.getText().toString().isEmpty()) {
                    showTip("请先选择年级");
                    return;
                }

                Intent term = new Intent(this, TermListActivity.class);
                startActivityForResult(term, 103);
                break;
            case R.id.ll_class:
                if (tvTerm.getText().toString().isEmpty()) {
                    showTip("请先选择学期");
                    return;
                }

                Intent intent = new Intent(this, ClassListActivity.class);
                startActivityForResult(intent, 104);
                break;
            case R.id.btn_complete:
                completeInfo();
                break;
        }
    }

    private void completeInfo() {
        JSONObject param = new JSONObject();
        param.put("roleType", "1");
        param.put("schoolId", SPUtils.getString("schoolId", ""));
        param.put("grade", SPUtils.getString("grade", ""));
        param.put("semeter", SPUtils.getString("term", ""));
        param.put("classId", SPUtils.getString("classId", ""));
        HttpUs.send(Deploy.setSchoolGradeClass(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                JSONObject data = JSON.parseObject(info.getData());
                //                SPUtils.setString(SPUtils.SCHOOL_NAME,data.getString("schoolName"));
                //                SPUtils.setString(SPUtils.CLASS_NAME,data.getString("className"));
                //                SPUtils.setString(SPUtils.SCHOOL_ID,SPUtils.getString("schoolId", ""));
                //                SPUtils.setString(SPUtils.CLASS_ID,SPUtils.getString("classId", ""));

                setTermInfo(info.getData());

                HintDiaUtils.createDialog(mContext).showSucceedDialog("设置成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == 1001) {
                    String schoolName = data.getStringExtra("schoolName");
                    tvSchool.setText(schoolName);

                    tvGrade.setText("");
                    tvTerm.setText("");
                    tvClass.setText("");
                }
                break;
            case 102:
                if (resultCode == 1002) {
                    String gradeName = data.getStringExtra("gradeName");
                    tvGrade.setText(gradeName);
                }
                break;
            case 103:
                if (resultCode == 1003) {
                    String termName = data.getStringExtra("termName");
                    tvTerm.setText(termName);
                }
                break;
            case 104:
                if (resultCode == 1004) {
                    String className = data.getStringExtra("className");
                    tvClass.setText(className);
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isMain) {
                showTip("请设置相关信息");
            } else {
                PerfectInfoActivity.this.finish();
            }
        }
        return true;
    }
}
