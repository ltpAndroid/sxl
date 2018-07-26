package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.DailyPractise;

import java.util.List;

public class PractiseAdapter extends BaseQuickAdapter<DailyPractise, BaseViewHolder> {


    public PractiseAdapter(int layoutResId, @Nullable List<DailyPractise> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyPractise item) {
        helper.setText(R.id.tv_pt_title, item.getTitle());
        helper.setText(R.id.tv_pt_start_time, item.getStart_time());
        helper.setText(R.id.tv_pt_teacher, item.getTeacher());
        helper.setText(R.id.tv_pt_type, item.getType());
        helper.setText(R.id.tv_pt_end_time, item.getEnd_time());
        int iconId = 0;
        String title = item.getTitle();
        if (title.equals("诵经典")) {
            iconId = R.drawable.icon_recite;
        } else if (title.equals("习汉字")) {
            iconId = R.drawable.icon_chinese;
        } else if (title.equals("练运算")) {
            iconId = R.drawable.icon_math;
        } else {
            iconId = R.mipmap.ic_launcher;
        }

        Glide.with(mContext).load(iconId).into((ImageView) helper.getView(R.id.iv_icon));
    }

}
