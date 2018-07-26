package com.dofun.sxl.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dofun.sxl.R;

import java.util.ArrayList;
import java.util.List;

public class RadarView extends View {
    //数据个数
    private int count = 3;
    //成绩圆点半径
    private int valueRadius = 8;
    //网格最大半径
    private float radius;
    //中心X
    private float centerX;
    //中心Y
    private float centerY;
    //雷达区画笔
    private Paint mainPaint;
    //文本画笔
    private Paint textPaint;
    //数据区画笔
    private Paint valuePaint;
    //标题文字
    private List<String> titles;
    //各维度分值
    private List<Double> data;
    //数据最大值
    private float maxValue = 100;
    //弧度
    private float angle = (float) (2 * Math.PI / 6);//中心与相邻两个内角相连的夹角角度
    //设置颜色组
    private int[] colors = new int[]{R.color.md_green_300, R.color.md_red_300, R.color.md_purple_300, R.color.main_color};


    public RadarView(Context context) {
        super(context);
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //雷达区画笔初始化
        mainPaint = new Paint();
        //mainPaint.setColor(setColor(R.color.main_color));
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(2);
        mainPaint.setStyle(Paint.Style.STROKE);
        //文本画笔初始化
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
        textPaint.setStrokeWidth(2);
        textPaint.setAntiAlias(true);
        //数据区（分数）画笔初始化
        valuePaint = new Paint();
        valuePaint.setColor(setColor(R.color.md_red_100));
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL);

        titles = new ArrayList<>();
        titles.add("科学精神");
        titles.add("健康生活");
        titles.add("实践创新");
        titles.add("责任担当");
        titles.add("学会学习");
        titles.add("人文底蕴");
        //count = titles.size();

        //默认分数
        data = new ArrayList<>();
        data.add(62.5);
        data.add(75.0);
        data.add(100.0);
        data.add(62.5);
        data.add(100.0);
        data.add(87.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.8f;
        centerX = w / 2;
        centerY = h / 2;
        //一旦size发生改变，重新绘制
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);//绘制蜘蛛网
        //drawLines(canvas);//绘制直线
        drawTitle(canvas);//绘制标题
        drawRegion(canvas);//绘制覆盖区域
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        //1度=1*PI/180   360度=2*PI   那么我们每旋转一次的角度为2*PI/内角个数

        //每个蛛丝之间的间距
        float r = (radius / 2) / 4;

        for (int i = 0; i < 4; i++) {
            mainPaint.setColor(setColor(colors[i]));
            //当前半径
            float curR = r * i + radius / 2;
            path.reset();
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    float x1 = centerX + curR;
                    float y1 = centerY;
                    path.moveTo(x1, y1);
                } else {
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }

    private void drawTitle(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;//文本高度
        float d = (radius / 2) / (count + 1);
        for (int i = 0; i < 6; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if (angle * i >= 0 && angle * i <= Math.PI / 2) {//第4象限：科学精神、健康生活
                canvas.drawText(titles.get(i), x + 2 * d, y + fontHeight / 4, textPaint);
            } else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {//第3象限：人文底蕴
                canvas.drawText(titles.get(i), x + 3 * d, y + fontHeight * 3 / 4, textPaint);
            } else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {//第2象限:实践创新
                float dis = textPaint.measureText(titles.get(i));//文本长度
                canvas.drawText(titles.get(i), x - dis / 2, y + fontHeight / 4, textPaint);
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {//第1象限//责任担当、学会学习
                float dis = textPaint.measureText(titles.get(i));//文本长度
                if (i == 3)
                    canvas.drawText(titles.get(3), x - dis / 2 + d, y + fontHeight / 4, textPaint);
                else
                    canvas.drawText(titles.get(4), x - dis / 2, y + 3 * fontHeight / 4, textPaint);
            }
        }
    }

    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        //valuePaint.setAlpha(255);
        for (int i = 0; i < 6; i++) {
            double percent = data.get(i) / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            //canvas.drawCircle(x,y,10,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        //绘制填充区域
        valuePaint.setAlpha(125);
        valuePaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, valuePaint);
    }

    private int setColor(int colorId) {
        return getResources().getColor(colorId);
    }
}
