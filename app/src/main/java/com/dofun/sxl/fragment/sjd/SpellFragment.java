package com.dofun.sxl.fragment.sjd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.SpellAdapter;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.fragment.BaseFragment;
import com.dofun.sxl.view.GridDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpellFragment extends BaseFragment {

    @BindView(R.id.spell_1)
    EditText spell1;
    @BindView(R.id.spell_2)
    EditText spell2;
    @BindView(R.id.spell_3)
    EditText spell3;
    @BindView(R.id.spell_4)
    EditText spell4;
    @BindView(R.id.spell_5)
    EditText spell5;
    @BindView(R.id.spell_6)
    EditText spell6;
    @BindView(R.id.spell_7)
    EditText spell7;
    Unbinder unbinder;
    @BindView(R.id.rv_spell)
    RecyclerView rvSpell;

    private List<EditText> editTexts = new ArrayList<>();
    private List<TopicDetail> detailList = new ArrayList<>();
    SpellAdapter adapter;

    public SpellFragment() {
        // Required empty public constructor
    }

    public static SpellFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        SpellFragment fragment = new SpellFragment();
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
        View view = inflater.inflate(R.layout.fragment_spell, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        editTexts.add(spell1);
        editTexts.add(spell2);
        editTexts.add(spell3);
        editTexts.add(spell4);
        editTexts.add(spell5);
        editTexts.add(spell6);
        editTexts.add(spell7);

        GridLayoutManager manager = new GridLayoutManager(mActivity, 3);
        rvSpell.setLayoutManager(manager);
        rvSpell.addItemDecoration(new GridDivider(mActivity, 2, setColor(R.color.md_grey_600)));
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);

        List<String> words = new ArrayList<>();
        String[] str = detailList.get(0).getDetail().split(",");
        for (String word : str) {
            words.add(word);
        }
        if (adapter == null) {
            adapter = new SpellAdapter(R.layout.item_spell, words);
            rvSpell.setAdapter(adapter);
        } else {
            adapter.replaceData(words);
        }

        final List<String> txtList = new ArrayList<>();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TextView word = (TextView) view;
                String txt = word.getText().toString();
                if (txtList.size() == 7) {
                    showTip("只能选取7个字组成诗句");
                    return;
                }
                word.setTextColor(setColor(R.color.md_red_500));
                word.setEnabled(false);
                txtList.add(txt);
                editTexts.get(txtList.size() - 1).setText(txt);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
