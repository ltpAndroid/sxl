package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.BookType;

import java.util.List;

public class BookTypeAdapter extends BaseQuickAdapter<BookType, BaseViewHolder> {
    public BookTypeAdapter(int layoutResId, @Nullable List<BookType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookType item) {
        helper.setText(R.id.item_teacher_name, item.getType());
        Glide.with(mContext).load(item.getResId()).into((ImageView) helper.getView(R.id.item_teacher_header));
    }
}
