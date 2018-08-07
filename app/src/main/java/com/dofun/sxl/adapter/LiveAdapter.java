package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.LiveList;

import java.util.List;

public class LiveAdapter extends BaseQuickAdapter<LiveList, BaseViewHolder> {
    public LiveAdapter(int layoutResId, @Nullable List<LiveList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveList item) {
        helper.setText(R.id.item_live_title, item.getLiveTitle())
                .setText(R.id.item_live_time, item.getLiveTime())
                .setText(R.id.item_live_duration, item.getLiveDuration());
        Glide.with(mContext).load(R.drawable.rank_header).into((ImageView) helper.getView(R.id.item_live_header));
        String state = item.getLiveState();
        if (state.equals("true")) {
            helper.setVisible(R.id.item_live_state, true);
        } else {
            helper.setVisible(R.id.item_live_state, false);
        }
    }
}
