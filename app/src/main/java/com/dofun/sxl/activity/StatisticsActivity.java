package com.dofun.sxl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Statistics;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.view.CircleProgress;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity {


    @BindView(R.id.score_full)
    CircleProgress scoreFull;
    @BindView(R.id.score_high)
    CircleProgress scoreHigh;
    @BindView(R.id.score_low)
    CircleProgress scoreLow;
    @BindView(R.id.score_average)
    CircleProgress scoreAverage;
    @BindView(R.id.pieChart)
    PieChart mChart;
    @BindView(R.id.tv_back_statistics)
    TextView tvBack;

    private int highScore;
    private int lowScore;
    private int averageScore;

    private float first;
    private float second;
    private float third;
    private float fourth;
    private float total;

    private List<Float> data = new ArrayList<>();
    private int[] colorIds = new int[]{R.color.md_red_500, R.color.md_green_500, R.color.main_color, R.color.md_orange_500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);

        initData();
        //initView();
    }

    private void initData() {
        HttpUs.send(Deploy.queryStatistics(), null, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                Statistics statistics = JSONObject.parseObject(info.getData(), Statistics.class);
                highScore = statistics.getMaxScore();
                lowScore = statistics.getMinScore();
                averageScore = (int) statistics.getAverageScore();

                first = statistics.getOneLevel();
                second = statistics.getTwoLevel();
                third = statistics.getThreeLevel();
                fourth = statistics.getFourLevel();
                total = statistics.getTotalCount();

                initView();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip("没有已提交的作业，无统计结果");
            }
        });
    }

    private void initView() {
        scoreHigh.setProgress(highScore);
        scoreLow.setProgress(lowScore);
        scoreAverage.setProgress(averageScore);

        initPieChart();
    }

    private void initPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setHoleRadius(80f);
        mChart.setTransparentCircleRadius(0f);

        mChart.setDrawCenterText(false);

        mChart.setRotationAngle(-36);
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(false);

        data.add(first * 100 / total);
        data.add(second * 100 / total);
        data.add(third * 100 / total);
        data.add(fourth * 100 / total);
        setData(4, 100);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < data.size(); i++) {
            entries.add(new PieEntry(data.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Homework Results");
        dataSet.setSliceSpace(0f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(setColor(R.color.md_red_500));
        colors.add(setColor(R.color.md_green_500));
        colors.add(setColor(R.color.main_color));
        colors.add(setColor(R.color.md_orange_500));
        dataSet.setColors(colors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        pieData.setValueTypeface(mTfLight);
        mChart.setData(pieData);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @OnClick(R.id.tv_back_statistics)
    public void onViewClicked() {
        finish();
    }
}
