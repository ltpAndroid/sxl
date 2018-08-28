package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.LiveAdapter;
import com.dofun.sxl.adapter.LiveTeacherAdapter;
import com.dofun.sxl.bean.LiveBean;
import com.dofun.sxl.bean.LiveTeacher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment {


    @BindView(R.id.rv_teacher)
    RecyclerView rvTeacher;
    @BindView(R.id.rv_live)
    RecyclerView rvLive;
    Unbinder unbinder;

    private LiveTeacherAdapter teacherAdapter;
    private List<LiveTeacher> teacherList = new ArrayList<>();

    private LiveAdapter adapter;
    private List<LiveBean> listList = new ArrayList<>();

    public LiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTeacher.setLayoutManager(manager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLive.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mActivity, R.drawable.divider_height));
        rvLive.addItemDecoration(divider);
    }

    private void initData() {
        teacherList.add(new LiveTeacher("张老师"));
        teacherList.add(new LiveTeacher("李老师"));
        teacherList.add(new LiveTeacher("宋老师"));
        teacherList.add(new LiveTeacher("王老师"));
        teacherList.add(new LiveTeacher("赵老师"));
        teacherList.add(new LiveTeacher("孙老师"));
        if (teacherAdapter == null) {
            teacherAdapter = new LiveTeacherAdapter(R.layout.item_teachser_list, teacherList);
            rvTeacher.setAdapter(teacherAdapter);
        } else {
            teacherAdapter.notifyDataSetChanged();
        }


        listList.add(new LiveBean("今天给大家讲一下勾股定理", "2018-07-05", "true", "00:13:58"));
        listList.add(new LiveBean("今天给大家讲一下勾股定理", "2018-07-05", "false", "00:23:58"));
        listList.add(new LiveBean("今天给大家讲一下勾股定理", "2018-07-05", "true", "00:13:50"));
        if (adapter == null) {
            adapter = new LiveAdapter(R.layout.item_live_list, listList);
            rvLive.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
