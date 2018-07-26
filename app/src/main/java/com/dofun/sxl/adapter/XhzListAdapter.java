package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.XhzListBean;

import java.util.ArrayList;
import java.util.List;

public class XhzListAdapter extends BaseQuickAdapter<XhzListBean, BaseViewHolder> {
    public XhzListAdapter(int layoutResId, @Nullable List<XhzListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XhzListBean item) {
        helper.setText(R.id.item_xhz_date, item.getDate());
        helper.setText(R.id.item_week, item.getWeek());
        helper.setText(R.id.item_score, item.getScore());
        helper.setText(R.id.item_diff_level, item.getDiffLevel());
        helper.setText(R.id.item_content, item.getContent());
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.main_color);
        colors.add(R.color.md_orange_500);
        colors.add(R.color.md_red_500);
        colors.add(R.color.md_light_blue_500);
        colors.add(R.color.md_teal_A200);
        colors.add(R.color.md_deep_orange_500);
        colors.add(R.color.md_purple_500);
        int index = (int) (Math.random() * colors.size());
        helper.getView(R.id.item_xhz_date).setBackgroundColor(mContext.getResources().getColor(colors.get(index)));
        helper.getView(R.id.item_line).setBackgroundColor(mContext.getResources().getColor(colors.get(index)));
        helper.getView(R.id.item_dot).setBackgroundColor(mContext.getResources().getColor(colors.get(index)));
        //        helper.getView(R.id.item_dot).setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_dot));
    }
}
