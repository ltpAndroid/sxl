package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.DailyPractise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PractiseAdapter extends BaseQuickAdapter<DailyPractise, BaseViewHolder> {


    public PractiseAdapter(int layoutResId, @Nullable List<DailyPractise> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyPractise item) {
        String startTime = TimeUtils.millis2String(item.getStartTime(), new SimpleDateFormat("yyyy-MM-dd"));
        String endTime = TimeUtils.millis2String(item.getEndTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        helper.setText(R.id.tv_pt_title, item.getCourseName());
        helper.setText(R.id.tv_pt_start_time, startTime);
        helper.setText(R.id.tv_pt_teacher, item.getTeacherName());
        helper.setText(R.id.tv_pt_type, "每日练习");
        helper.setText(R.id.tv_pt_end_time, endTime);
        int iconId = 0;
        String title = item.getCourseName();
        TextView left = helper.getView(R.id.tv_pt_left);
        TextView right = helper.getView(R.id.tv_pt_right);
        TextView tv = helper.getView(R.id.tv_pt_title);
        List<TextView> tvList = new ArrayList<>();
        tvList.add(left);
        tvList.add(right);
        tvList.add(tv);
        if (title.equals("诵经典")) {
            iconId = R.drawable.icon_recite;
            setColor(tvList, R.color.md_orange_500);
        } else if (title.equals("习汉字")) {
            iconId = R.drawable.icon_chinese;
            setColor(tvList, R.color.md_light_blue_500);
        } else if (title.equals("练运算")) {
            iconId = R.drawable.icon_math;
            setColor(tvList, R.color.md_red_500);
        } else {
            iconId = R.mipmap.ic_launcher;
        }

        Glide.with(mContext).load(iconId).into((ImageView) helper.getView(R.id.iv_icon));
    }

    private void setColor(List<TextView> textViewList, int colorId) {
        for (TextView tv : textViewList) {
            tv.setTextColor(mContext.getResources().getColor(colorId));
        }
    }
}
