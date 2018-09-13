package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Book;

import java.util.List;

public class BookAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {
        helper.setText(R.id.item_book_name, item.getImageTitle())
                .setText(R.id.item_book_introduction, item.getContent());
        String imgPath = Deploy.ImgURL + item.getImagePath();
        Glide.with(mContext).load(imgPath).error(R.drawable.classic_book).into((ImageView) helper.getView(R.id.item_book_cover));
    }
}
