package com.dofun.sxl.fragment.sjd;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.BaseFragment;
import com.dofun.sxl.view.Line;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LigatureFragment extends BaseFragment {

    @BindView(R.id.left_1)
    TextView left1;
    @BindView(R.id.right_1)
    TextView right1;
    @BindView(R.id.left_2)
    TextView left2;
    @BindView(R.id.right_2)
    TextView right2;
    @BindView(R.id.left_3)
    TextView left3;
    @BindView(R.id.right_3)
    TextView right3;
    @BindView(R.id.left_4)
    TextView left4;
    @BindView(R.id.right_4)
    TextView right4;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;
    Unbinder unbinder;
    private List<TopicDetail> detailList = new ArrayList<>();

    List<int[]> pointList = new ArrayList<>();
    boolean isLeftChecked = false;
    boolean isRightChecked = false;

    int leftTag;
    int rightTag;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;

    public LigatureFragment() {
        // Required empty public constructor
    }

    public static LigatureFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        LigatureFragment fragment = new LigatureFragment();
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
        View view = inflater.inflate(R.layout.fragment_ligature, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        List<String> contents = new ArrayList<>();
        if (detailList.size() == 0) {
            showTip("没有布置该题型");
            return;
        }
        String detail = detailList.get(0).getDetail();
        String[] sentence = detail.split("@");
        for (String content : sentence) {
            String[] topic = content.split("。");
            for (String txt : topic) {
                contents.add(txt);
            }
        }
        left1.setText(contents.get(0) + "。");
        right1.setText(contents.get(1));
        left2.setText(contents.get(2) + "。");
        right2.setText(contents.get(3));
        left3.setText(contents.get(4) + "。");
        right3.setText(contents.get(5));
        left4.setText(contents.get(6) + "。");
        right4.setText(contents.get(7));
    }

    @OnClick({R.id.left_1, R.id.right_1, R.id.left_2, R.id.right_2, R.id.left_3, R.id.right_3, R.id.left_4, R.id.right_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_1:
                leftTag = 1;
                isLeftChecked = true;
                isRightChecked = false;
                pointList.clear();
                int[] left1Point = getRightXY(view);
                Log.i("left_1", left1Point[0] + "," + left1Point[1]);
                pointList.add(left1Point);
                setLeftCheckedColor();
                break;
            case R.id.right_1:
                if (isLeftChecked && !isRightChecked) {
                    int[] right1Point = getLeftXY(view);
                    Log.i("right_1", right1Point[0] + "," + right1Point[1]);
                    pointList.add(right1Point);
                    Line line1 = new Line(mActivity);
                    line1.setPointList(pointList);
                    rlLayout.addView(line1);
                    isRightChecked = true;
                    isLeftChecked = false;
                    rightTag = 1;
                    setEnableByTag();
                }
                break;
            case R.id.left_2:
                leftTag = 2;
                isLeftChecked = true;
                isRightChecked = false;
                pointList.clear();
                int[] left2Point = getRightXY(view);
                Log.i("left_1", left2Point[0] + "," + left2Point[1]);
                pointList.add(left2Point);
                setLeftCheckedColor();
                break;
            case R.id.right_2:
                if (isLeftChecked && !isRightChecked) {
                    int[] right2Point = getLeftXY(view);
                    Log.i("right_2", right2Point[0] + "," + right2Point[1]);
                    pointList.add(right2Point);
                    Line line2 = new Line(mActivity);
                    line2.setPointList(pointList);
                    rlLayout.addView(line2);
                    isRightChecked = true;
                    isLeftChecked = false;
                    rightTag = 2;
                    setEnableByTag();
                }
                break;
            case R.id.left_3:
                leftTag = 3;
                isLeftChecked = true;
                isRightChecked = false;
                pointList.clear();
                int[] left3Point = getRightXY(view);
                Log.i("left_1", left3Point[0] + "," + left3Point[1]);
                pointList.add(left3Point);
                setLeftCheckedColor();
                break;
            case R.id.right_3:
                if (isLeftChecked && !isRightChecked) {
                    int[] right3Point = getLeftXY(view);
                    Log.i("right_3", right3Point[0] + "," + right3Point[1]);
                    pointList.add(right3Point);
                    Line line3 = new Line(mActivity);
                    line3.setPointList(pointList);
                    rlLayout.addView(line3);
                    isRightChecked = true;
                    isLeftChecked = false;
                    rightTag = 3;
                    setEnableByTag();
                }
                break;
            case R.id.left_4:
                leftTag = 4;
                isLeftChecked = true;
                isRightChecked = false;
                pointList.clear();
                int[] left4Point = getRightXY(view);
                Log.i("left_1", left4Point[0] + "," + left4Point[1]);
                pointList.add(left4Point);
                setLeftCheckedColor();
                break;
            case R.id.right_4:
                if (isLeftChecked && !isRightChecked) {
                    int[] right4Point = getLeftXY(view);
                    Log.i("right_4", right4Point[0] + "," + right4Point[1]);
                    pointList.add(right4Point);
                    Line line4 = new Line(mActivity);
                    line4.setPointList(pointList);
                    rlLayout.addView(line4);
                    isRightChecked = true;
                    isLeftChecked = false;
                    rightTag = 4;
                    setEnableByTag();
                }
                break;
        }
    }

    /**
     * 获取控件右侧垂直方向中心点
     *
     * @param view
     */
    private int[] getRightXY(View view) {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        int[] position = new int[2];
        view.getLocationOnScreen(position);
        int rightX = position[0] + view.getWidth();
        int rightY = position[1] /*+ view.getHeight() / 2*/ - result - ConvertUtils.dp2px(105);
        int[] point = new int[]{rightX, rightY};
        return point;
    }

    /**
     * 获取控件左侧垂直方向中心点
     *
     * @param view
     */
    private int[] getLeftXY(View view) {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        int[] position = new int[2];
        view.getLocationOnScreen(position);
        int rightX = position[0];
        int rightY = position[1] /*+ view.getHeight() / 2*/ - result - ConvertUtils.dp2px(105);
        int[] point = new int[]{rightX, rightY};
        return point;
    }

    private void setEnableByTag() {
        switch (rightTag) {
            case 1:
                setLeftAndRight(right1);
                break;
            case 2:
                setLeftAndRight(right2);
                break;
            case 3:
                setLeftAndRight(right3);
                break;
            case 4:
                setLeftAndRight(right4);
                break;
        }
        changeLeft();
    }

    private void setLeftAndRight(TextView tv) {
        tv.setOnClickListener(null);
        String strTv = tv.getText().toString();
        switch (leftTag) {
            case 1:
                left1.setOnClickListener(null);
                String strLeft1 = left1.getText().toString();
                answer1 = strLeft1 + strTv;
                break;
            case 2:
                left2.setOnClickListener(null);
                String strLeft2 = left2.getText().toString();
                answer2 = strLeft2 + strTv;
                break;
            case 3:
                left3.setOnClickListener(null);
                String strLeft3 = left3.getText().toString();
                answer3 = strLeft3 + strTv;
                break;
            case 4:
                left4.setOnClickListener(null);
                String strLeft4 = left4.getText().toString();
                answer4 = strLeft4 + strTv;
                break;
        }
    }

    private void setLeftCheckedColor() {
        switch (leftTag) {
            case 1:
                left1.setTextColor(Color.RED);
                left2.setTextColor(Color.GRAY);
                left3.setTextColor(Color.GRAY);
                left4.setTextColor(Color.GRAY);
                break;
            case 2:
                left2.setTextColor(Color.RED);
                left1.setTextColor(Color.GRAY);
                left3.setTextColor(Color.GRAY);
                left4.setTextColor(Color.GRAY);
                break;
            case 3:
                left3.setTextColor(Color.RED);
                left1.setTextColor(Color.GRAY);
                left2.setTextColor(Color.GRAY);
                left4.setTextColor(Color.GRAY);
                break;
            case 4:
                left4.setTextColor(Color.RED);
                left1.setTextColor(Color.GRAY);
                left2.setTextColor(Color.GRAY);
                left3.setTextColor(Color.GRAY);
                break;
        }
    }

    private void changeLeft() {
        left1.setTextColor(Color.GRAY);
        left2.setTextColor(Color.GRAY);
        left3.setTextColor(Color.GRAY);
        left4.setTextColor(Color.GRAY);
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
            case EventConstants.SJD_LX:
                List<Answer> list = new ArrayList<>();
                String answer = answer1 + answer2 + answer3 + answer4;
                TopicDetail topicDetail = detailList.get(0);
                Answer answerBean = new Answer();
                answerBean.setTopicId(topicDetail.getId() + "");
                answerBean.setAnswer(topicDetail.getAnalysis());
                answerBean.setAnswerU(answer);
                answerBean.setScore(topicDetail.getFraction() + "");
                if (topicDetail.getAnalysis().equals(answer)) {
                    //showTip("正确");
                    answerBean.setIsRight("1");
                } else {
                    //showTip("错误");
                    answerBean.setIsRight("0");
                }
                list.add(answerBean);
                AnswerConstants.setSjdAnswer(102, list);

                break;
        }
    }
}
