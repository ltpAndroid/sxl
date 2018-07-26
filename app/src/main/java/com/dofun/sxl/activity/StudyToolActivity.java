package com.dofun.sxl.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.BookAdapter;
import com.dofun.sxl.bean.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyToolActivity extends BaseActivity {

    @BindView(R.id.finish_from_books)
    TextView tvBack;
    @BindView(R.id.rv_book)
    RecyclerView rvBook;

    private BookAdapter adapter;
    private List<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_tool);
        ButterKnife.bind(this);
        setStateBarColor();

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBook.setLayoutManager(manager);
        rvBook.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        bookList.add(new Book("中华传统文化简易读本", "徐志国 杨振威", "简介：" + getStringByID(R.string.brief_introduction)));
        bookList.add(new Book("国学经典", "杨永胜", "简介：" + getStringByID(R.string.brief_introduction)));
        bookList.add(new Book("朝读经典", "徐志国 杨振威", "简介：" + getStringByID(R.string.brief_introduction)));
        if (adapter == null) {
            adapter = new BookAdapter(R.layout.item_book_list, bookList);
            rvBook.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.finish_from_books)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_from_books:
                finish();
                break;
        }
    }
}
