package com.dofun.sxl.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.dofun.sxl.R;
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

    private List<Float> data = new ArrayList<>();
    private int[] colorIds = new int[]{R.color.md_red_500, R.color.md_green_500, R.color.main_color, R.color.md_orange_500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
    }

    private void initView() {
        scoreFull.setProgress(100);
        scoreHigh.setProgress(90);
        scoreLow.setProgress(80);
        scoreAverage.setProgress(85);

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

        data.add(50f);
        data.add(25f);
        data.add(15f);
        data.add(10f);
        setData(4, 100);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData(int count, float range) {

        float mult = range;

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
}
