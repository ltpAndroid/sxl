package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.MistakeListActivity;
import com.dofun.sxl.adapter.MistakeNoteAdapter;
import com.dofun.sxl.bean.MistakeNote;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.SPUtils;
import com.dofun.sxl.view.DialogWaiting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.base.TBaseAdapter;
import com.timmy.tdialog.list.TListDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NoteFragment extends BaseFragment {

    @BindView(R.id.rv_mistake)
    RecyclerView rvMistake;
    @BindView(R.id.refresh_mistake)
    SmartRefreshLayout refreshMistake;
    Unbinder unbinder;
    @BindView(R.id.iv_sjd)
    ImageView ivSjd;
    @BindView(R.id.title_sjd)
    LinearLayout titleSjd;
    @BindView(R.id.iv_xhz)
    ImageView ivXhz;
    @BindView(R.id.title_xhz)
    LinearLayout titleXhz;
    @BindView(R.id.iv_lys)
    ImageView ivLys;
    @BindView(R.id.title_lys)
    LinearLayout titleLys;
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;

    private MistakeNoteAdapter adapter;
    private List<MistakeNote> mistakeList = new ArrayList<>();
    private List<MistakeNote> orgList = new ArrayList<>();

    private String timeType = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initView() {
        ivSjd.setVisibility(View.VISIBLE);
        ivXhz.setVisibility(View.INVISIBLE);
        ivLys.setVisibility(View.INVISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMistake.setLayoutManager(manager);
        rvMistake.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        refreshMistake.setRefreshHeader(new ClassicsHeader(mActivity))
                .setRefreshFooter(new ClassicsFooter(mActivity));
    }

    private void initData() {
        timeType = SPUtils.getString("timeType", "1");
        changeData("10");

        refreshMistake.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showTip("刷新完成");
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_sjd, R.id.title_xhz, R.id.title_lys, R.id.tv_filtrate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_sjd:
                ivSjd.setVisibility(View.VISIBLE);
                ivXhz.setVisibility(View.INVISIBLE);
                ivLys.setVisibility(View.INVISIBLE);

                changeData("10");
                break;
            case R.id.title_xhz:
                ivXhz.setVisibility(View.VISIBLE);
                ivSjd.setVisibility(View.INVISIBLE);
                ivLys.setVisibility(View.INVISIBLE);

                changeData("11");
                break;
            case R.id.title_lys:
                ivLys.setVisibility(View.VISIBLE);
                ivXhz.setVisibility(View.INVISIBLE);
                ivSjd.setVisibility(View.INVISIBLE);

                changeData("12");
                break;
            case R.id.tv_filtrate:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        String[] data = {"近一周", "近一月", "近半年", "近一年"};
        new TListDialog.Builder(mActivity.getSupportFragmentManager())
                .setHeight(600)
                .setScreenWidthAspect(mActivity, 0.5f)
                .setGravity(Gravity.CENTER)
                .setAdapter(new TBaseAdapter<String>(R.layout.item_simple_text, Arrays.asList(data)) {

                    @Override
                    protected void onBind(BindViewHolder holder, int position, String s) {
                        holder.setText(R.id.tv, s);
                    }
                })
                .setOnAdapterItemClickListener(new TBaseAdapter.OnAdapterItemClickListener<String>() {
                    @Override
                    public void onItemClick(BindViewHolder holder, int position, String s, TDialog tDialog) {
                        switch (s) {
                            case "近一周":
                                timeType = "1";
                            case "近一月":
                                timeType = "2";
                            case "近半年":
                                timeType = "3";
                            case "近一年":
                                timeType = "4";
                        }
                        SPUtils.setString("timeType", timeType);
                        tDialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void changeData(final String courseId) {
        final DialogWaiting dialog = DialogWaiting.build(mActivity);
        dialog.show();

        JSONObject param = new JSONObject();
        param.put("courseId", courseId);
        param.put("timeType", SPUtils.getString("timeType", "1"));
        param.put("roleType", "1");
        HttpUs.send(Deploy.getWrongBook(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                Log.i("onSuccess", info + "");
                dialog.dimiss();

                mistakeList = JSONArray.parseArray(info.getData(), MistakeNote.class);
                for (MistakeNote note : mistakeList) {
                    note.setCourseId(courseId);
                }
                if (adapter == null) {
                    adapter = new MistakeNoteAdapter(R.layout.item_mistake_note, mistakeList);
                    rvMistake.setAdapter(adapter);
                } else {
                    adapter.replaceData(mistakeList);
                }

                setListener();
            }

            @Override
            public void onFailure(ResInfo info) {
                Log.i("onFailure", info + "");
                showTip(info.getMsg());
            }
        });

    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MistakeNote mistakeNote = (MistakeNote) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mistakeNote", mistakeNote);
                if (mistakeNote.getCourseId().equals("10")) {
                    ActivityUtils.startActivity(bundle, MistakeListActivity.class);
                }
            }
        });
    }
}
