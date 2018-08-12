package com.dofun.sxl.fragment.sjd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.GapAdapter;
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
public class GapFillFragment extends BaseFragment {


    @BindView(R.id.rv_gap)
    RecyclerView rvGap;
    Unbinder unbinder;
    @BindView(R.id.gap_answer_1)
    LinearLayout answer1;
    @BindView(R.id.gap_answer_2)
    LinearLayout answer2;
    @BindView(R.id.gap_answer_3)
    LinearLayout answer3;
    @BindView(R.id.gap_answer_4)
    LinearLayout answer4;

    private List<TopicDetail> detailList = new ArrayList<>();
    private GapAdapter adapter;

    public GapFillFragment() {
        // Required empty public constructor
    }

    public static GapFillFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        GapFillFragment fragment = new GapFillFragment();
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
        View view = inflater.inflate(R.layout.fragment_gap_fill, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGap.setLayoutManager(manager);

        List<LinearLayout> layoutList = new ArrayList<>();
        layoutList.add(answer1);
        layoutList.add(answer2);
        layoutList.add(answer3);
        layoutList.add(answer4);
        for (int i = 0; i < detailList.size(); i++) {
            layoutList.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);

        if (adapter == null) {
            adapter = new GapAdapter(R.layout.item_gap_fill, detailList);
            rvGap.setAdapter(adapter);
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
