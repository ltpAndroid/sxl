package com.dofun.sxl.fragment.sjd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.adapter.GapAdapter;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
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
    @BindView(R.id.tv_answer)
    TextView tvAnswer;
    @BindView(R.id.et_answer_1)
    EditText etAnswer1;
    @BindView(R.id.et_answer_2)
    EditText etAnswer2;
    @BindView(R.id.et_answer_3)
    EditText etAnswer3;
    @BindView(R.id.et_answer_4)
    EditText etAnswer4;

    private List<TopicDetail> detailList = new ArrayList<>();
    private GapAdapter adapter;
    private List<String> answerList = new ArrayList<>();
    List<EditText> editTextList = new ArrayList<>();

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

        if (detailList.size() == 0) {
            tvAnswer.setVisibility(View.GONE);
        } else {
            tvAnswer.setVisibility(View.VISIBLE);
        }

        List<LinearLayout> layoutList = new ArrayList<>();
        layoutList.add(answer1);
        layoutList.add(answer2);
        layoutList.add(answer3);
        layoutList.add(answer4);

        editTextList.add(etAnswer1);
        editTextList.add(etAnswer2);
        editTextList.add(etAnswer3);
        editTextList.add(etAnswer4);

        for (int i = 0; i < detailList.size(); i++) {
            layoutList.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        if (detailList.size() == 0) {
            showTip("没有布置该题型");
            return;
        }
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

    @Override
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.SJD_TK:
                String toasts = "";
                List<Answer> list = new ArrayList<>();
                for (int i = 0; i < detailList.size(); i++) {
                    String answer = editTextList.get(i).getText().toString();
                    TopicDetail topicDetail = detailList.get(i);
                    Answer answerBean = new Answer();
                    answerBean.setTopicId(topicDetail.getId() + "");
                    answerBean.setAnswer(topicDetail.getAnalysis());
                    answerBean.setAnswerU(answer);
                    answerBean.setScore(topicDetail.getFraction() + "");
                    if (topicDetail.getAnalysis().equals(answer)) {
                        toasts += "正确";
                        answerBean.setIsRight("1");
                    } else {
                        toasts += "错误";
                        answerBean.setIsRight("0");
                    }
                    list.add(answerBean);
                    answerList.add(answer);
                }
                AnswerConstants.setSjdAnswer(101, list);
                showTip(toasts);

                String str = "";
                for (String answer : answerList) {
                    str += (answer + "@");
                }
                //AnswerConstants.setSjdAnswer(101, str.substring(0, str.length() - 1));
                answerList.clear();
                //showTip(AnswerConstants.sjdMap.get(101));
                break;
            default:
                break;
        }
    }
}
