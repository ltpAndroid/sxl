package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.TopicDetail;

import java.util.List;

public class ChooseAdapter extends BaseQuickAdapter<TopicDetail, BaseViewHolder> {

    public ChooseAdapter(int layoutResId, @Nullable List<TopicDetail> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicDetail item) {
        helper.setText(R.id.item_choose_position, (helper.getLayoutPosition() + 1) + "、")
                .setText(R.id.item_choose_detail, item.getDetail())
                .setText(R.id.item_choose_A, item.getOptionList().get(0).getDetail())
                .setText(R.id.item_choose_B, item.getOptionList().get(1).getDetail())
                .setText(R.id.item_choose_C, item.getOptionList().get(2).getDetail())
                .addOnClickListener(R.id.item_choose_A)
                .addOnClickListener(R.id.item_choose_B)
                .addOnClickListener(R.id.item_choose_C);

    }

}
