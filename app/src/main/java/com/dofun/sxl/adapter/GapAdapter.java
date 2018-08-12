package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.TopicDetail;

import java.util.List;

public class GapAdapter extends BaseQuickAdapter<TopicDetail, BaseViewHolder> {
    public GapAdapter(int layoutResId, @Nullable List<TopicDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicDetail item) {
        helper.setText(R.id.item_gap_position, (helper.getLayoutPosition() + 1) + "、")
                .setText(R.id.item_gap_detail, item.getDetail());
    }
}
