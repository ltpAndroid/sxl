package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.LiveTeacher;

import java.util.List;

public class LiveTeacherAdapter extends BaseQuickAdapter<LiveTeacher, BaseViewHolder> {
    public LiveTeacherAdapter(int layoutResId, @Nullable List<LiveTeacher> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveTeacher item) {
        helper.setText(R.id.item_teacher_name, item.getName());
        Glide.with(mContext).load(R.drawable.user_header).into((ImageView) helper.getView(R.id.item_teacher_header));
    }
}
