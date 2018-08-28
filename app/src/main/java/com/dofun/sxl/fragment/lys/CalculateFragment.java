package com.dofun.sxl.fragment.lys;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.StrAdapter;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalculateFragment extends BaseFragment {
    @BindView(R.id.current_num)
    TextView tvCurrent;
    @BindView(R.id.total_num)
    TextView tvTotal;
    @BindView(R.id.topic_score)
    TextView tvScore;
    @BindView(R.id.topic_detail)
    ImageView ivDetail;
    @BindView(R.id.topic_result)
    EditText etResult;
    @BindView(R.id.rv_calculator)
    RecyclerView rvCalculator;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.num_layout)
    LinearLayout numLayout;
    Unbinder unbinder;

    StrAdapter adapter;
    List<String> str = new ArrayList<>();
    String result = "";
    List<String> topic = new ArrayList<>();

    private List<TopicDetail> detailList = new ArrayList<>();

    public CalculateFragment() {
        // Required empty public constructor
    }

    public static CalculateFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        CalculateFragment fragment = new CalculateFragment();
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
        View view = inflater.inflate(R.layout.fragment_fast, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        disableShowInput(etResult);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 4);
        rvCalculator.setLayoutManager(manager);
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        initData();
    }

    private void initData() {
        str.add("1");
        str.add("2");
        str.add("3");
        str.add(">");
        str.add("4");
        str.add("5");
        str.add("6");
        str.add("<");
        str.add("7");
        str.add("8");
        str.add("9");
        str.add("=");
        str.add("0");
        str.add(".");
        str.add("删除");
        str.add("/");
        adapter = new StrAdapter(R.layout.item_keyboard, str);
        rvCalculator.setAdapter(adapter);
        setListener();

        if (detailList.size() == 0) {
            numLayout.setVisibility(View.GONE);
            rvCalculator.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            etResult.setVisibility(View.GONE);
            showTip("没有布置该题型");
            return;
        }
        tvScore.setText(detailList.get(0).getFraction() + "");
        String data = Deploy.ImgURL + detailList.get(0).getDetail();
        Glide.with(mActivity).load(data).into(ivDetail);
        tvTotal.setText(detailList.size() + "");
        for (int i = 0; i < detailList.size(); i++) {
            topic.add(Deploy.ImgURL + detailList.get(i).getDetail());
        }
    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 14) {
                    if (result.length() == 0) {
                        showTip("结果已删除");
                        return;
                    }
                    result = result.substring(0, result.length() - 1);
                    etResult.setText(result);
                } else {
                    String num = (String) adapter.getItem(position);
                    if (result.length() < 5) {
                        result += num;
                        etResult.setText(result);
                    }
                }
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        int current = Integer.parseInt(tvCurrent.getText().toString());
        if (current < topic.size()) {
            Glide.with(mActivity).load(topic.get(current)).into(ivDetail);
            tvScore.setText(detailList.get(current).getFraction() + "");
            tvCurrent.setText(String.valueOf(++current));

            etResult.setText("");
            result = "";
        } else {
            showTip("本题型已做完，请点击下一题");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
