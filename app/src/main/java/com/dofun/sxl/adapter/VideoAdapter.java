package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.VideoBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {
    public VideoAdapter(int layoutResId, @Nullable List<VideoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = TimeUtils.millis2String(item.getCreateTime(), format);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String duration = TimeUtils.millis2String(Long.valueOf(item.getSecond()) * 1000, format);
        helper.setText(R.id.item_video_time, duration)
                .setText(R.id.item_video_title, item.getName())
                .setText(R.id.item_video_sender, item.getTeacher().getName())
                .setText(R.id.item_video_send_time, time);
        String imgUrl = Deploy.ImgURL + item.getImageUrl();
        Glide.with(mContext).load(imgUrl).into((ImageView) helper.getView(R.id.item_video_img));
    }
}
