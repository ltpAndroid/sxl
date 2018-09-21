package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.TopicRightBean;

import java.util.List;

public class TopicImgAdapter extends BaseQuickAdapter<TopicRightBean, BaseViewHolder> {
    public TopicImgAdapter(int layoutResId, @Nullable List<TopicRightBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TopicRightBean item) {
        helper.setText(R.id.item_isRight_position, (helper.getLayoutPosition() + 1) + "");
        int rseId = item.isRight() ? R.drawable.right : R.drawable.wrong;
        Glide.with(mContext).load(rseId).into((ImageView) helper.getView(R.id.item_isRight_icon));
        String imgPath = Deploy.ImgURL + item.getDetail();
        Glide.with(mContext).load(imgPath).into((ImageView) helper.getView(R.id.iv_lys_content));
    }
}
