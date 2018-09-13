package com.dofun.sxl.activity.personal;

import android.os.Bundle;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.view.RadarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalLevelActivity extends BaseActivity {

    @BindView(R.id.radar_grade)
    RadarView radarGrade;
    @BindView(R.id.radar_synthesize)
    RadarView radarSynthesize;

    List<String> titles = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_level);
        ButterKnife.bind(this);

        titles.add("练运算(85)");
        titles.add("习汉字(55)");
        titles.add("诵经典(95)");
        titleList.add("科学精神");
        titleList.add("健康生活");
        titleList.add("实践创新");
        titleList.add("责任担当");
        titleList.add("学会学习");
        titleList.add("人文底蕴");
        radarGrade.setCount(3);
        radarGrade.setRectColor(R.color.md_green_200);
        radarGrade.setTitles(titles);
        radarSynthesize.setCount(6);
        radarSynthesize.setRectColor(R.color.md_red_200);
        radarSynthesize.setTitles(titleList);
    }

}
