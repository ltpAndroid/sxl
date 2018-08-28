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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
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

    private List<TopicDetail> detailList = new ArrayList<>();
    List<String> topic = new ArrayList<>();

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

    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        if (detailList.size() == 0) {
            numLayout.setVisibility(View.GONE);
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

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        int current = Integer.parseInt(tvCurrent.getText().toString());
        if (current < topic.size()) {
            Glide.with(mActivity).load(topic.get(current)).into(ivDetail);
            tvScore.setText(detailList.get(current).getFraction() + "");
            tvCurrent.setText(String.valueOf(++current));

            rbA.setChecked(false);
            rbB.setChecked(false);
            rbC.setChecked(false);

        } else {
            showTip("本题型已做完");
        }
    }
}
