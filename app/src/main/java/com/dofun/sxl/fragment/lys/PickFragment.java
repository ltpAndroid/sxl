package com.dofun.sxl.fragment.lys;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
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
public class PickFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.rb_A)
    RadioButton rbA;
    @BindView(R.id.rb_B)
    RadioButton rbB;
    @BindView(R.id.rb_C)
    RadioButton rbC;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.current_num)
    TextView tvCurrent;
    @BindView(R.id.total_num)
    TextView tvTotal;
    @BindView(R.id.num_layout)
    LinearLayout numLayout;
    @BindView(R.id.topic_score)
    TextView tvScore;
    @BindView(R.id.rg_pick)
    RadioGroup rgPick;

    private List<TopicDetail> detailList = new ArrayList<>();
    List<String> topic = new ArrayList<>();
    int current;

    public PickFragment() {
        // Required empty public constructor
    }

    public static PickFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        PickFragment fragment = new PickFragment();
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
        View view = inflater.inflate(R.layout.fragment_pick, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        current = Integer.parseInt(tvCurrent.getText().toString());
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        if (detailList.size() == 0) {
            numLayout.setVisibility(View.GONE);
            rgPick.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            showTip("没有布置该题型");
            return;
        }
        String imgPath = Deploy.ImgURL + detailList.get(0).getDetail();
        Glide.with(mActivity).load(imgPath).into(ivDetail);
        tvTotal.setText(detailList.size() + "");
        tvScore.setText(detailList.get(0).getFraction() + "");
        for (int i = 0; i < detailList.size(); i++) {
            topic.add(Deploy.ImgURL + detailList.get(i).getDetail());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Map<Integer, String> answerMap = new HashMap<>();

    @OnClick({R.id.btn_next, R.id.rb_A, R.id.rb_B, R.id.rb_C})
    public void onViewClicked(View view) {
        int topicId = detailList.get(current - 1).getId();
        String answerContent = "";
        switch (view.getId()) {
            case R.id.btn_next:
                if (current < topic.size()) {
                    Glide.with(mActivity).load(topic.get(current)).into(ivDetail);
                    tvScore.setText(detailList.get(current).getFraction() + "");

                    tvCurrent.setText(String.valueOf(++current));
                    rbA.setChecked(false);
                    rbB.setChecked(false);
                    rbC.setChecked(false);
                    EventBus.getDefault().post(new EventBusBean<Integer>(1, EventConstants.LYS_POSITION, current));
                } else {
                    showTip("本题型已做完");
                }
                break;
            case R.id.rb_A:
                answerContent = rbA.getText().toString();
                break;
            case R.id.rb_B:
                answerContent = rbB.getText().toString();
                break;
            case R.id.rb_C:
                answerContent = rbC.getText().toString();
                break;
        }
        answerMap.put(topicId, answerContent);
    }


    @Override
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.LYS_XZ:
                List<Integer> idList = new ArrayList<>();
                for (int i = 0; i < detailList.size(); i++) {
                    idList.add(detailList.get(i).getId());
                }
                for (Integer topicId : idList) {
                    if (!answerMap.containsKey(topicId)) {
                        answerMap.put(topicId, "");
                    }
                }

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
                AnswerConstants.setLysAnswer(124, list);
                //showTip(toasts);
                break;
        }
    }
}
