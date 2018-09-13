package com.dofun.sxl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookDetailActivity extends BaseActivity {

    @BindView(R.id.tv_back_book)
    TextView tvBack;
    @BindView(R.id.iv_book_cover)
    ImageView ivCover;
    @BindView(R.id.tv_book_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Book book = (Book) getIntent().getSerializableExtra("book");
        if (EmptyUtils.isEmpty(book.getContentImagePath())) {
            ivCover.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(Deploy.ImgURL + book.getContentImagePath()).into(ivCover);
        }
        tvContent.setText(book.getContent());
    }

    @OnClick(R.id.tv_back_book)
    public void onViewClicked() {
        finish();
    }
}
