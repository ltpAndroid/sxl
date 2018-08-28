package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.MistakeNote;

import java.text.SimpleDateFormat;
import java.util.List;

public class MistakeNoteAdapter extends BaseQuickAdapter<MistakeNote, BaseViewHolder> {
    public MistakeNoteAdapter(int layoutResId, @Nullable List<MistakeNote> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MistakeNote item) {
        String createTime = TimeUtils.millis2String(item.getCreateTime(), new SimpleDateFormat("yyyy-MM-dd"));
        String endTime = TimeUtils.millis2String(item.getEndTime(), new SimpleDateFormat("yyyy-MM-dd"));
        helper.setText(R.id.item_note_assign_date, createTime)
                .setText(R.id.item_note_teacher, item.getNickname())
                .setText(R.id.item_note_type, "每日练习")
                .setText(R.id.item_note_end_time, endTime);
    }
}
