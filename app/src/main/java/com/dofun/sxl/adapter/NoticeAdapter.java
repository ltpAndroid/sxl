package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Notice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NoticeAdapter extends BaseQuickAdapter<Notice, BaseViewHolder> {
    public NoticeAdapter(int layoutResId, @Nullable List<Notice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice item) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = TimeUtils.millis2String(item.getPublishTime(), format);
        helper.setText(R.id.item_notice_title, item.getTitle())
                .setText(R.id.item_notice_time, time)
                .setText(R.id.item_notice_content, item.getContent());
        if (item.getReadStatus() == 1) {
            helper.setVisible(R.id.item_notice_state, false);
        } else {
            helper.setVisible(R.id.item_notice_state, true);
        }
    }
}
