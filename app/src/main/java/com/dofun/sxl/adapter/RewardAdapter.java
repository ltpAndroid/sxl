package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.RewardBean;

import java.util.List;

public class RewardAdapter extends BaseQuickAdapter<RewardBean, BaseViewHolder> {
    public RewardAdapter(int layoutResId, @Nullable List<RewardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RewardBean item) {
        helper.setText(R.id.item_rank_num, item.getRank());
        helper.setText(R.id.item_rank_name, item.getName());
        helper.setText(R.id.item_rank_praise, item.getPraiseNum());
        helper.setText(R.id.item_rank_medal, item.getMedalNum());
        Glide.with(mContext).load(item.getHeaderId()).into((ImageView) helper.getView(R.id.item_rank_header));
        int position = helper.getLayoutPosition();
        if (position == 0) {
            helper.setVisible(R.id.item_rank_crown, true)
                    .setImageResource(R.id.item_rank_crown, R.drawable.crown_gold);
        } else if (position == 1) {
            helper.setVisible(R.id.item_rank_crown, true)
                    .setImageResource(R.id.item_rank_crown, R.drawable.crown_silver);
        } else if (position == 2) {
            helper.setVisible(R.id.item_rank_crown, true)
                    .setImageResource(R.id.item_rank_crown, R.drawable.crown_copper);
        } else {
            helper.setVisible(R.id.item_rank_crown, false);
        }
    }
}
