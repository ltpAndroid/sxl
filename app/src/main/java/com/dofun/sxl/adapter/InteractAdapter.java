package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Interact;
import com.dofun.sxl.view.CircleImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class InteractAdapter extends BaseQuickAdapter<Interact, BaseViewHolder> {
    public InteractAdapter(int layoutResId, @Nullable List<Interact> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Interact item) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = TimeUtils.millis2String(item.getCreateTime(), format);
        helper.setText(R.id.item_comment_name, item.getNickname())
                .setText(R.id.item_comment_content, item.getContext())
                .setText(R.id.item_comment_time, time);
        TextView tv = helper.getView(R.id.item_comment_content);
        tv.setMaxLines(1);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Glide.with(mContext).load(item.getAvatarUrl()).error(R.drawable.rank_header).into((CircleImageView) helper.getView(R.id.item_comment_img));
    }
}
