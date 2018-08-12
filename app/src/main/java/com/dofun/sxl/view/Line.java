package com.dofun.sxl.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Line extends View {
    private Paint paint;
    private List<int[]> pointList;

    public Line(Context context) {
        super(context);
        init();
    }

    public Line(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Line(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        pointList = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(pointList.get(0)[0] - 10, pointList.get(0)[1], pointList.get(1)[0] - 10, pointList.get(1)[1], paint);
    }

    public List<int[]> getPointList() {
        return pointList;
    }

    public void setPointList(List<int[]> pointList) {
        this.pointList = pointList;
    }
}
