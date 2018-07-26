package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.ListBean;

import java.util.List;

public class ListBeanAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder> {
    public ListBeanAdapter(int layoutResId, @Nullable List<ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        helper.setText(R.id.item_name, item.getName());
        Glide.with(mContext).load(item.getIconId()).into((ImageView) helper.getView(R.id.item_icon));
    }
}
