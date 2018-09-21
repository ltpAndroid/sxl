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
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.BaseFragment;
import com.tandong.sa.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    int current;

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
        current = Integer.parseInt(tvCurrent.getText().toString());
        disableShowInput(etResult);
        GridLayoutManager manager = new GridLayoutManager(mActivity, 4);
        rvCalculator.setLayoutManager(manager);

        if (detailList.size() == 1) {
            btnNext.setVisibility(View.INVISIBLE);
        }
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

    private Map<Integer, String> answerMap = new HashMap<>();
    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        if (current < topic.size()) {
            Glide.with(mActivity).load(topic.get(current)).into(ivDetail);
            tvScore.setText(detailList.get(current).getFraction() + "");
            int topicId = detailList.get(current - 1).getId();
            String answerContent = etResult.getText().toString();
            answerMap.put(topicId, answerContent);

            tvCurrent.setText(String.valueOf(++current));
            EventBus.getDefault().post(new EventBusBean<Integer>(1, EventConstants.LYS_POSITION, current));
            etResult.setText("");
            result = "";

            if (current == topic.size()) {
                btnNext.setVisibility(View.INVISIBLE);
            }
        } /*else {
            showTip("本题型已做完，请点击下一题");
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.LYS_JS:
                answerMap.put(detailList.get(current - 1).getId(), etResult.getText().toString());

                List<Answer> list = new ArrayList<>();
                String toasts = "";
                for (int i = 0; i < detailList.size(); i++) {
                    TopicDetail topicDetail = detailList.get(i);
                    int topicId = topicDetail.getId();
                    String answer = answerMap.get(topicId);
                    Answer answerBean = new Answer();
                    answerBean.setTopicId(topicDetail.getId() + "");
                    answerBean.setAnswerU(answer);
                    answerBean.setAnswer(topicDetail.getAnalysis());
                    answerBean.setScore(topicDetail.getFraction() + "");
                    if (answer.equals(topicDetail.getAnalysis())) {
                        answerBean.setIsRight("1");
                        toasts += "正确";
                    } else {
                        answerBean.setIsRight("0");
                        toasts += "错误";
                    }
                    list.add(answerBean);
                }
                AnswerConstants.setLysAnswer(122, list);
                //showTip(toasts);
                break;
        }
    }
}
