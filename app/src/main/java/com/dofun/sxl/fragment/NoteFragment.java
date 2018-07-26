package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.PractiseAdapter;
import com.dofun.sxl.bean.DailyPractise;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NoteFragment extends BaseFragment {

    @BindView(R.id.rv_mistake)
    RecyclerView rvMistake;
    @BindView(R.id.refresh_mistake)
    SmartRefreshLayout refreshMistake;
    Unbinder unbinder;
    @BindView(R.id.title_layout)
    TabLayout titleLayout;

    private PractiseAdapter adapter;
    private List<DailyPractise> mistakeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initView() {
        titleLayout.addTab(titleLayout.newTab().setCustomView(setTabItem("诵经典", R.drawable.recite_book)));
        titleLayout.addTab(titleLayout.newTab().setCustomView(setTabItem("习汉字", R.drawable.write_word)));
        titleLayout.addTab(titleLayout.newTab().setCustomView(setTabItem("练运算", R.drawable.practise_num)));
    }

    private View setTabItem(String text, int imgId) {
        View tabItem = LayoutInflater.from(mActivity).inflate(R.layout.custom_tabitem, null);
        ImageView icon = tabItem.findViewById(R.id.tab_icon);
        icon.setImageResource(imgId);
        TextView title = tabItem.findViewById(R.id.tab_text);
        title.setText(text);
        return tabItem;
    }

    private void initData() {
        mistakeList = new ArrayList<>();
        mistakeList.add(new DailyPractise("诵经典", "2018-07-07", "张慧敏", "口语评测", "2018-07-09 21:00"));
        mistakeList.add(new DailyPractise("习汉字", "2018-07-07", "张慧敏", "书法练习", "2018-07-09 21:00"));
        mistakeList.add(new DailyPractise("练运算", "2018-07-07", "张慧敏", "心算口算", "2018-07-09 21:00"));


        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMistake.setLayoutManager(manager);
        rvMistake.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        refreshMistake.setRefreshHeader(new ClassicsHeader(mActivity))
                .setRefreshFooter(new ClassicsFooter(mActivity));

        if (adapter == null) {
            adapter = new PractiseAdapter(R.layout.item_practise, mistakeList);
            rvMistake.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        refreshMistake.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mistakeList.add(new DailyPractise("习汉字", "2018-07-07", "张慧敏", "书法练习", "2018-07-09 21:00"));
                        refreshLayout.finishLoadMore();
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showTip("刷新完成");
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
