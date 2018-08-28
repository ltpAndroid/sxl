package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.VideoBean;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {
    public VideoAdapter(int layoutResId, @Nullable List<VideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        helper.setText(R.id.item_video_time, item.getVideoTime())
                .setText(R.id.item_video_title, item.getVideoTitle())
                .setText(R.id.item_video_sender, item.getSender())
                .setText(R.id.item_video_receiver, item.getReceiver())
                .setText(R.id.item_video_send_time, item.getSendTime());
        Glide.with(mContext).load(R.drawable.mskt).into((ImageView) helper.getView(R.id.item_video_img));
    }
}
