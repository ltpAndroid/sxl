package com.dofun.sxl.fragment.sjd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.JudgeAdapter;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class JudgeFragment extends BaseFragment {

    @BindView(R.id.rv_judge)
    RecyclerView rvJudge;
    Unbinder unbinder;

    private List<TopicDetail> detailList = new ArrayList<>();
    JudgeAdapter adapter;

    public JudgeFragment() {
        // Required empty public constructor
    }

    public static JudgeFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        JudgeFragment fragment = new JudgeFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", topicDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            ArrayList<TopicDetail> list = (ArrayList<TopicDetail>) args.getSerializable("data");
            detailList.clear();
            detailList.addAll(list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_judge, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJudge.setLayoutManager(manager);
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        if (adapter == null) {
            adapter = new JudgeAdapter(R.layout.item_judge, detailList);
            rvJudge.setAdapter(adapter);
        } else {
            adapter.replaceData(detailList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
