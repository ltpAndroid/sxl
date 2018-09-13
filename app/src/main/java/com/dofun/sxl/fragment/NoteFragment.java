package com.dofun.sxl.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.mistake.MistakeLysActivity;
import com.dofun.sxl.activity.mistake.MistakeSjdActivity;
import com.dofun.sxl.adapter.MistakeNoteAdapter;
import com.dofun.sxl.bean.MistakeList;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.SPUtils;
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
    @BindView(R.id.tv_filtrate)
    TextView tvFiltrate;
    @BindView(R.id.rl_sjd)
    RelativeLayout rlSjd;
    @BindView(R.id.rl_xhz)
    RelativeLayout rlXhz;
    @BindView(R.id.rl_lys)
    RelativeLayout rlLys;
    @BindView(R.id.icon_sjd)
    ImageView iconSjd;
    @BindView(R.id.icon_xhz)
    ImageView iconXhz;
    @BindView(R.id.icon_lys)
    ImageView iconLys;

    private MistakeNoteAdapter adapter;
    private List<MistakeList> mistakeList = new ArrayList<>();
    private List<MistakeList> orgList = new ArrayList<>();

    private String timeType = "";
    String courseId = "10";

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

        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMistake.setLayoutManager(manager);
        //rvMistake.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        refreshMistake.setRefreshHeader(new ClassicsHeader(mActivity))
                .setRefreshFooter(new ClassicsFooter(mActivity));
    }

    private void initData() {
        timeType = SPUtils.getString("timeType", "1");
        changeData(courseId);

        refreshMistake.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                        changeData(courseId);
                    }
                }, 2000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                changeData(courseId);
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_sjd, R.id.rl_xhz, R.id.rl_lys, R.id.tv_filtrate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_sjd:
                iconSjd.setImageResource(R.drawable.sjd_1);
                iconLys.setImageResource(R.drawable.lys_0);
                iconXhz.setImageResource(R.drawable.xhz_0);

                courseId = "10";
                changeData(courseId);
                break;
            case R.id.rl_xhz:
                iconSjd.setImageResource(R.drawable.sjd_0);
                iconLys.setImageResource(R.drawable.lys_0);
                iconXhz.setImageResource(R.drawable.xhz_1);

                courseId = "11";
                changeData(courseId);
                break;
            case R.id.rl_lys:
                iconSjd.setImageResource(R.drawable.sjd_0);
                iconLys.setImageResource(R.drawable.lys_1);
                iconXhz.setImageResource(R.drawable.xhz_0);

                courseId = "12";
                changeData(courseId);
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
                                break;
                            case "近一月":
                                timeType = "2";
                                break;
                            case "近半年":
                                timeType = "3";
                                break;
                            case "近一年":
                                timeType = "4";
                                break;
                        }
                        SPUtils.setString("timeType", timeType);
                        tDialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void changeData(final String courseId) {
        //        final DialogWaiting dialog = DialogWaiting.build(mActivity);
        //        dialog.show();

        JSONObject param = new JSONObject();
        param.put("courseId", courseId);
        param.put("timeType", SPUtils.getString("timeType", "1"));
        param.put("roleType", "1");
        param.put("page", "1");
        HttpUs.send(Deploy.getWrongBook(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                //                dialog.dimiss();

                mistakeList = JSONArray.parseArray(info.getData(), MistakeList.class);
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
                LogUtils.i(info.toString());
                showTip(info.getMsg());
                rvMistake.setVisibility(View.GONE);
            }
        });

    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MistakeList mistakeList = (MistakeList) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mistakeNote", mistakeList);
                if (mistakeList.getFkId() == 10) {
                    ActivityUtils.startActivity(bundle, MistakeSjdActivity.class);
                }
                if (mistakeList.getFkId() == 11) {
                    //ActivityUtils.startActivity(bundle, MistakeSjdActivity.class);
                }
                if (mistakeList.getFkId() == 12) {
                    ActivityUtils.startActivity(bundle, MistakeLysActivity.class);
                }
            }
        });
    }
}
