package com.dofun.sxl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Statistics;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.view.CircleProgress;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsActivity extends BaseActivity implements OnChartValueSelectedListener {


    @BindView(R.id.score_full)
    CircleProgress scoreFull;
    @BindView(R.id.score_sjd)
    CircleProgress scoreSjd;
    @BindView(R.id.score_xhz)
    CircleProgress scoreXhz;
    @BindView(R.id.score_lys)
    CircleProgress scoreLys;
    @BindView(R.id.pieChart)
    PieChart mPieChart;
    @BindView(R.id.tv_back_statistics)
    TextView tvBack;
    @BindView(R.id.bar_chart)
    BarChart mBarChart;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.radar_view)
    RadarChart radarView;
    @BindView(R.id.rb_sjd)
    RadioButton rbSjd;
    @BindView(R.id.rb_xhz)
    RadioButton rbXhz;
    @BindView(R.id.rb_lys)
    RadioButton rbLys;
    @BindView(R.id.rg_sxl)
    RadioGroup rgSxl;

    private int sjdScore;
    private int xhzScore;
    private int lysScore;

    private float first;
    private float second;
    private float third;
    private float fourth;
    private float total;

    private String[] type = {"1", "2", "3", "4", "5"};

    //private List<Statistics.AnswerWrongListBean> answerWrongList = new ArrayList<>();

    private String timeType = "1";

    private TopRightMenu topRightMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ButterKnife.bind(this);

        initData();
        //initView();
        //        initRadarView();
        //rbSjd.setChecked(true);
    }

    private void initData() {
        JSONObject param = new JSONObject();
        param.put("timeType", timeType);
        HttpUs.send(Deploy.getStudentReport(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                List<Statistics> statisticsList = JSONArray.parseArray(info.getData(), Statistics.class);

                for (int i = 0; i < 2; i++) {
                    initView(statisticsList);
                }
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        }, mContext, "数据加载中");
    }

    private void initView(List<Statistics> dataList) {
        for (Statistics sta :
                dataList) {
            String courseId = sta.getCourseId();
            switch (courseId) {
                case "10":
                    scoreSjd.setProgress(sta.getTotalScore());
                    sjdScore = sta.getTotalScore();
                    break;
                case "11":
                    scoreXhz.setProgress(sta.getTotalScore());
                    xhzScore = sta.getTotalScore();
                    break;
                case "12":
                    scoreLys.setProgress(sta.getTotalScore());
                    lysScore = sta.getTotalScore();
                    break;
            }
        }

        //initPieChart();
        initRadarView();
        //initBarChart();
    }


    private void initRadarView() {
        radarView.getDescription().setEnabled(false);
        radarView.setWebLineWidth(1f);
        radarView.setWebColor(Color.WHITE);
        radarView.setWebLineWidthInner(1f);
        radarView.setWebColorInner(Color.DKGRAY);
        //        radarView.setWebAlpha(100);
        radarView.getLegend().setEnabled(false);
        radarView.setRotationEnabled(false);

        setRadarData();

        XAxis xAxis = radarView.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(14f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] fk = new String[]{"诵经典", "习汉字", "练运算"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return fk[(int) value % fk.length];
            }
        });

        YAxis yAxis = radarView.getYAxis();
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(3, false);
        yAxis.setTextSize(14f);
        yAxis.setXOffset(0);
        yAxis.setYOffset(0);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);
    }

    private void setRadarData() {
        ArrayList<RadarEntry> entries = new ArrayList<>();
        entries.add(new RadarEntry(sjdScore));
        entries.add(new RadarEntry(xhzScore));
        entries.add(new RadarEntry(lysScore));

        RadarDataSet set = new RadarDataSet(entries, "fk statistics");
        set.setColor(setColor(R.color.md_teal_200));
        set.setFillColor(setColor(R.color.md_teal_200));
        set.setDrawFilled(true);
        set.setFillAlpha(200);
        set.setLineWidth(1f);
        set.setDrawHighlightCircleEnabled(true);
        set.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set);
        RadarData data = new RadarData(sets);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(12f);
        data.setDrawValues(true);
        //        data.setValueTextColor(Color.WHITE);

        radarView.setData(data);
        radarView.invalidate();
    }

    private void initPieChart() {
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        mPieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setHoleRadius(80f);
        mPieChart.setTransparentCircleRadius(0f);

        mPieChart.setDrawCenterText(false);

        mPieChart.setRotationAngle(-36);
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(false);

        List<Float> data = new ArrayList<>();
        if ((first + second + third + fourth) != 0) {
            data.add(first * 100 / total);
            data.add(second * 100 / total);
            data.add(third * 100 / total);
            data.add(fourth * 100 / total);
        }
        setData(data);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData(List<Float> data) {

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
        mPieChart.setData(pieData);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    private void initBarChart() {
        //设置表格上的点，被点击的时候，的回调函数
        mBarChart.setOnChartValueSelectedListener(this);

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setMaxVisibleValueCount(60);
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.animateY(2000);
        mBarChart.getLegend().setEnabled(false);
        //mBarChart.setHighlightPerTapEnabled(false);//设置点击Item高亮是否可用


        IAxisValueFormatter xAxisFormatter = new IAxisValueFormatter() {
            private String[] types = new String[]{"练运算", "诵经典", "习汉字"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return types[(int) value % types.length];
            }
        };

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(14f);
        xAxis.setXOffset(0f);
        xAxis.setYOffset(0f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        //        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);


        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        //        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(30f);

        mBarChart.getAxisRight().setEnabled(false);

        //setBarData(answerWrongList);
    }

    //    private void setBarData(List<Statistics.AnswerWrongListBean> dataList) {
    //        List<BarEntry> yVals = new ArrayList<>();
    //
    //        if (dataList.size() == 3) {
    //            for (int i = 0; i < dataList.size(); i++) {
    //                int kind = dataList.get(i).getFkId();
    //                int count = dataList.get(i).getCount();
    //                switch (kind) {
    //                    case 10:
    //                        yVals.add(new BarEntry(01f, count));
    //                        break;
    //                    case 11:
    //                        yVals.add(new BarEntry(02f, count));
    //                        break;
    //                    case 12:
    //                        yVals.add(new BarEntry(03f, count));
    //                        break;
    //                }
    //            }
    //        } else if (dataList.size() == 1) {
    //            int kind = dataList.get(0).getFkId();
    //            int count = dataList.get(0).getCount();
    //            switch (kind) {
    //                case 10:
    //                    yVals.add(new BarEntry(01f, count));
    //                    yVals.add(new BarEntry(02f, 0));
    //                    yVals.add(new BarEntry(03f, 0));
    //                    break;
    //                case 11:
    //                    yVals.add(new BarEntry(01f, 0));
    //                    yVals.add(new BarEntry(02f, count));
    //                    yVals.add(new BarEntry(03f, 0));
    //                    break;
    //                case 12:
    //                    yVals.add(new BarEntry(01f, 0));
    //                    yVals.add(new BarEntry(02f, 0));
    //                    yVals.add(new BarEntry(03f, count));
    //                    break;
    //            }
    //        } else if (dataList.size() == 2) {
    //            int count1 = 0;
    //            int count2 = 0;
    //            int count3 = 0;
    //            for (int i = 0; i < dataList.size(); i++) {
    //                int kind = dataList.get(i).getFkId();
    //                switch (kind) {
    //                    case 10:
    //                        count1 = dataList.get(i).getCount();
    //                        break;
    //                    case 11:
    //                        count2 = dataList.get(i).getCount();
    //                        break;
    //                    case 12:
    //                        count3 = dataList.get(i).getCount();
    //                        break;
    //                }
    //            }
    //            yVals.add(new BarEntry(01f, count1));
    //            yVals.add(new BarEntry(02f, count2));
    //            yVals.add(new BarEntry(03f, count3));
    //        } else {
    //            yVals.add(new BarEntry(01f, 0));
    //            yVals.add(new BarEntry(02f, 0));
    //            yVals.add(new BarEntry(03f, 0));
    //        }
    //
    //
    //        BarDataSet setData = new BarDataSet(yVals, "Data Set");
    //        setData.setColors(setColor(R.color.main_color));
    //        setData.setDrawValues(true);
    //
    //        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
    //        dataSets.add(setData);
    //
    //        BarData data = new BarData(dataSets);
    //        data.setBarWidth(0.5f);
    //        data.setValueTextSize(10f);
    //        mBarChart.setData(data);
    //        mBarChart.setFitBars(true);
    //
    //    }

    @OnClick({R.id.tv_back_statistics, R.id.ll_more, R.id.rb_sjd, R.id.rb_xhz, R.id.rb_lys})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_statistics:
                finish();
                break;
            case R.id.ll_more:
                //ivMore.setImageResource(R.drawable.more_up);
                showRightTop();
                break;
            case R.id.rb_sjd:
                ActivityUtils.startActivity(StatisticsSjdActivity.class);
                break;
            case R.id.rb_xhz:

                break;
            case R.id.rb_lys:

                break;
        }

    }

    private void showRightTop() {
        topRightMenu = new TopRightMenu(this);
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("本日"));
        menuItems.add(new MenuItem("本周"));
        menuItems.add(new MenuItem("本月"));
        menuItems.add(new MenuItem("本学期"));
        menuItems.add(new MenuItem("本学年"));
        topRightMenu.addMenuList(menuItems)
                .setWidth(RecyclerView.LayoutParams.WRAP_CONTENT)
                .setHeight(RecyclerView.LayoutParams.WRAP_CONTENT)
                .showIcon(false)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        tvPeriod.setText(menuItems.get(position).getText());
                        timeType = type[position];
                        initData();
                        //ivMore.setImageResource(R.drawable.more_down);
                    }
                })
                .showAsDropDown(llMore, -25, 30);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
