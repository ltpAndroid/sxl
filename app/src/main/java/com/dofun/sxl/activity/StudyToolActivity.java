package com.dofun.sxl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.BookAdapter;
import com.dofun.sxl.adapter.BookTypeAdapter;
import com.dofun.sxl.bean.Book;
import com.dofun.sxl.bean.BookType;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
    @BindView(R.id.rv_type)
    RecyclerView rvType;
    @BindView(R.id.refresh_book)
    SmartRefreshLayout refreshBook;

    private BookAdapter adapter;
    private List<Book> bookList = new ArrayList<>();

    private BookTypeAdapter typeAdapter;
    private List<BookType> typeList = new ArrayList<>();

    private String type = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_tool);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvType.setLayoutManager(layoutManager);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBook.setLayoutManager(manager);
        rvBook.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        refreshBook.setRefreshHeader(new ClassicsHeader(this))
                .setRefreshFooter(new ClassicsFooter(this));
    }

    private void initData() {

        typeList.add(new BookType(getStringByID(R.string.hzbs), R.drawable.hzbs));
        typeList.add(new BookType(getStringByID(R.string.zwfd), R.drawable.zwfd));
        typeList.add(new BookType(getStringByID(R.string.gxjc), R.drawable.gxjc));
        typeList.add(new BookType(getStringByID(R.string.jdhb), R.drawable.jdhb));
        typeList.add(new BookType(getStringByID(R.string.kply), R.drawable.kply));
        typeList.add(new BookType(getStringByID(R.string.cgkd), R.drawable.cgkd));
        if (typeAdapter == null) {
            typeAdapter = new BookTypeAdapter(R.layout.item_teachser_list, typeList);
            rvType.setAdapter(typeAdapter);
        } else {
            typeAdapter.notifyDataSetChanged();
        }
        setListener();

        queryBooks();
    }

    int oldPosition = -1;

    private void setListener() {
        //type列表点击监听
        typeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (oldPosition != position) {
                    TextView tvType = (TextView) adapter.getViewByPosition(rvType, position, R.id.item_teacher_name);
                    tvType.setTextColor(Color.RED);
                    if (oldPosition != -1) {
                        TextView tvOldType = (TextView) adapter.getViewByPosition(rvType, oldPosition, R.id.item_teacher_name);
                        tvOldType.setTextColor(setColor(R.color.md_grey_600));
                    }
                    oldPosition = position;
                }
                switch (position) {
                    case 0:
                        type = "1";
                        break;
                    case 1:
                        type = "2";
                        break;
                    case 2:
                        type = "3";
                        break;
                    case 3:
                        type = "4";
                        break;
                    case 4:
                        type = "5";
                        break;
                    case 5:
                        type = "6";
                        break;
                }
                refreshBook.autoRefresh();
            }
        });


        //刷新监听
        refreshBook.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryBooks();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                queryBooks();
                refreshLayout.finishRefresh();
            }
        });

    }

    private void queryBooks() {
        if (StringUtils.isEmpty(type)) {
            return;
        }

        JSONObject param = new JSONObject();
        param.put("status", "1");
        param.put("type", type);
        HttpUs.send(Deploy.studyTool(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                bookList = JSONArray.parseArray(info.getData(), Book.class);
                if (adapter == null) {
                    adapter = new BookAdapter(R.layout.item_book_list, bookList);
                    rvBook.setAdapter(adapter);
                } else {
                    adapter.replaceData(bookList);
                }

                //book列表点击监听
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Book book = (Book) adapter.getItem(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("book", book);
                        ActivityUtils.startActivity(bundle, BookDetailActivity.class);
                    }
                });
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
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
