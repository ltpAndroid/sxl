package com.dofun.sxl.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.adapter.CommentAdapter;
import com.dofun.sxl.bean.Comment;
import com.dofun.sxl.bean.VideoBean;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnBindViewListener;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tcking.github.com.giraffeplayer2.DefaultPlayerListener;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.PlayerListener;
import tcking.github.com.giraffeplayer2.PlayerManager;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoDetailActivity extends BaseActivity {

    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.iv_coverView)
    ImageView ivCoverView;
    @BindView(R.id.refresh_comment)
    SmartRefreshLayout refreshComment;
    @BindView(R.id.icon_play)
    ImageView iconPlay;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_praise)
    TextView tvPraise;

    private int aspectRatio = VideoInfo.AR_ASPECT_FILL_PARENT;
    private VideoBean videoBean;
    private List<Comment> commentList = new ArrayList<>();
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initData() {
        videoBean = (VideoBean) getIntent().getSerializableExtra("videoBean");
        initVideoPlayer();
        initComment();
    }

    private void initComment() {
        int videoId = videoBean.getId();
        JSONObject param = new JSONObject();
        param.put("itemId", String.valueOf(videoId));
        HttpUs.send(Deploy.queryItemCommentList(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                commentList = JSONArray.parseArray(info.getData(), Comment.class);
                if (adapter == null) {
                    adapter = new CommentAdapter(R.layout.item_comment, commentList);
                    rvComment.setAdapter(adapter);
                } else {
                    adapter.replaceData(commentList);
                }
                tvCommentNum.setText(String.valueOf(commentList.size()));
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void initVideoPlayer() {
        String videoTitle = videoBean.getName();
        String videoUrl = videoBean.getVideoUrl();
        videoView.setVideoPath(videoUrl);
        VideoInfo videoInfo = videoView.getVideoInfo();
        videoInfo.setShowTopBar(true).setTitle(videoTitle)
                .addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "multiple_requests", 1L))
                .setAspectRatio(aspectRatio)
                .setCurrentVideoAsCover(true);
        videoView.setPlayerListener(listener);
        videoView.getPlayer();

        String imageUrl = Deploy.ImgURL + videoBean.getImageUrl();
        Glide.with(this).load(imageUrl).into(ivCoverView);
    }

    private PlayerListener listener = new DefaultPlayerListener() {
        @Override
        public void onCompletion(GiraffePlayer giraffePlayer) {
            showTip("视频播放完毕");
        }

        @Override
        public void onStart(GiraffePlayer giraffePlayer) {
            ivCoverView.setVisibility(View.GONE);
            iconPlay.setVisibility(View.GONE);
            showTip("开始播放视频");
        }
    };

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvComment.setLayoutManager(manager);
        rvComment.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        refreshComment.setRefreshHeader(new ClassicsHeader(this))
                .setRefreshFooter(new ClassicsFooter(this));
        refreshComment.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1500);
                initComment();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1500);
                initComment();
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PlayerManager.getInstance().onConfigurationChanged(newConfig);
        //隐藏标题栏
        getSupportActionBar().hide();
    }

    @OnClick({R.id.icon_play, R.id.iv_comment, R.id.tv_collect, R.id.tv_praise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_play:
                videoView.getPlayer().start();
                break;
            case R.id.iv_comment:
                evaluateDialog();
                break;
            case R.id.tv_collect:
                String typeCollect = "3";
                String numCollect = tvCollect.getText().toString();
                int countCollect = Integer.parseInt(numCollect);
                updateNum(typeCollect, countCollect);
                break;
            case R.id.tv_praise:
                String typePraise = "4";
                String numPraise = tvPraise.getText().toString();
                int countPraise = Integer.parseInt(numPraise);
                updateNum(typePraise, countPraise);
                break;
        }
    }

    private void updateNum(final String type, final int num) {
        JSONObject param = new JSONObject();
        param.put("type", type);
        param.put("itemId", String.valueOf(videoBean.getId()));
        param.put("num", String.valueOf(num));
        HttpUs.send(Deploy.addItemInteract(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                int newNum = num;
                if (type.equals("3")) {
                    tvCollect.setText(String.valueOf(++newNum));
                } else if (type.equals("4")) {
                    tvPraise.setText(String.valueOf(++newNum));
                }
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void evaluateDialog() {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_evaluate)
                .setScreenWidthAspect(this, 1.0f)
                .setGravity(Gravity.BOTTOM)
                .addOnClickListener(R.id.btn_send, R.id.tv_cancel)
                .setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        final EditText editText = viewHolder.getView(R.id.et_content);
                        editText.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) VideoDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(editText, 0);
                            }
                        });
                    }
                })
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, final TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.btn_send:
                                EditText editText = viewHolder.getView(R.id.et_content);
                                String content = editText.getText().toString().trim();
                                if (TextUtils.isEmpty(content)) {
                                    showTip("内容不能为空!");
                                    return;
                                }
                                JSONObject param = new JSONObject();
                                param.put("itemId", String.valueOf(videoBean.getId()));
                                param.put("content", content);
                                HttpUs.send(Deploy.addItemComment(), param, new HttpUs.CallBackImp() {
                                    @Override
                                    public void onSuccess(ResInfo info) {
                                        LogUtils.i(info.toString());
                                        refreshComment.autoRefresh();
                                        tDialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(ResInfo info) {
                                        LogUtils.i(info.toString());
                                        showTip(info.getMsg());
                                    }
                                });
                                break;
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                        }
                    }
                }).create()
                .show();
    }
}