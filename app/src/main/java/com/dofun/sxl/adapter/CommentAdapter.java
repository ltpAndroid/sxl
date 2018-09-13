package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Comment;
import com.dofun.sxl.view.CircleImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {
    public CommentAdapter(int layoutResId, @Nullable List<Comment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = TimeUtils.millis2String(item.getCreateTime(), format);
        helper.setText(R.id.item_comment_name, item.getUsername())
                .setText(R.id.item_comment_time, time)
                .setText(R.id.item_comment_content, item.getContent());
        Glide.with(mContext).load(R.drawable.rank_header).into((CircleImageView) helper.getView(R.id.item_comment_img));
    }
}
