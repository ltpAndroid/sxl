package com.dofun.sxl.fragment.sjd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.JudgeAdapter;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Integer, Integer> answerMap = new HashMap<>();

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
        if (detailList.size() == 0) {
            showTip("没有布置该题型");
            return;
        }
        if (adapter == null) {
            adapter = new JudgeAdapter(R.layout.item_judge, detailList);
            rvJudge.setAdapter(adapter);
        } else {
            adapter.replaceData(detailList);
        }

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TopicDetail topicDetail = (TopicDetail) adapter.getItem(position);
                RadioButton radioButton = (RadioButton) view;

                for (int i = 0; i < topicDetail.getOptionList().size(); i++) {
                    String str = radioButton.getText().toString();
                    if (str.equals(topicDetail.getOptionList().get(i).getDetail())) {
                        int rbId = topicDetail.getOptionList().get(i).getId();
                        answerMap.put(topicDetail.getId(), rbId);
                    }
                }
            }
        });
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
            case EventConstants.SJD_PD:
                List<Integer> idList = new ArrayList<>();
                for (int i = 0; i < detailList.size(); i++) {
                    idList.add(detailList.get(i).getId());
                }
                for (Integer topicId : idList) {
                    if (!answerMap.containsKey(topicId)) {
                        answerMap.put(topicId, 0);
                    }
                }

                List<String> answerList = new ArrayList<>();
                for (int i = 0; i < detailList.size(); i++) {
                    answerList.add(detailList.get(i).getAnswer());
                }

                List<Answer> list = new ArrayList<>();
                String toasts = "";
                for (int i = 0; i < detailList.size(); i++) {
                    TopicDetail topicDetail = detailList.get(i);
                    int topicId = idList.get(i);
                    String answer = answerList.get(i);
                    Answer answerBean = new Answer();
                    answerBean.setTopicId(topicDetail.getId() + "");
                    answerBean.setAnswer(answer);
                    answerBean.setAnswerU(topicId + "");
                    answerBean.setScore(topicDetail.getFraction() + "");
                    if (answer.equals(String.valueOf(answerMap.get(topicId)))) {
                        toasts += "正确";
                        answerBean.setIsRight("1");
                    } else {
                        toasts += "错误";
                        answerBean.setIsRight("0");
                    }
                    list.add(answerBean);
                }
                AnswerConstants.setSjdAnswer(105, list);
                //showTip(toasts);

                String str = "";
                for (Map.Entry<Integer, Integer> entry : answerMap.entrySet()) {
                    str += entry.getValue() + "@";
                }
                //AnswerConstants.setSjdAnswer(105, str.substring(0, str.length() - 1));
                //showTip(AnswerConstants.sjdMap.get(105));
                break;
        }
    }
}
