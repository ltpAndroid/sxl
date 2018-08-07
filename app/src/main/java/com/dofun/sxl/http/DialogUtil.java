package com.dofun.sxl.http;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.tandong.sa.zUImageLoader.utils.L;


/**
 * 进度等候dialog
 *
 * @author Administrator
 */
public class DialogUtil extends Dialog {
    private static DialogUtil dialogutil = null;
    private Context context;

    private DialogUtil(Context context) {
        super(context);
        this.context = context;
    }

    private DialogUtil(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    /*
     * 初始代
     */
    public static DialogUtil createDialog(Context context) {
        try {
            if (dialogutil != null) {
                dialogutil.dismiss();
                dialogutil.cancel();
                dialogutil = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialogutil = new DialogUtil(context, R.style.CustomProgressDialog);
        dialogutil.setContentView(R.layout.loading_fullscreen);
        dialogutil.getWindow().getAttributes().gravity = Gravity.CENTER;
        dialogutil.setCancelable(false);// 点击外面不会自动消失
        return dialogutil;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (dialogutil == null) {
            return;
        }
        // Animation anim =
        // AnimationUtils.loadAnimation(context,R.anim.progress_round);
        // // 动画应用到控件上
        // ImageView iv = (ImageView)
        // dialogutil.findViewById(R.id.loadingImageView);
        // // 图片应用动画,开始播放
        // iv.startAnimation(anim);
        // // setMessage(strMessage);

        iv = (ImageView) dialogutil.findViewById(R.id.loadingImageView);
        setFlickerAnimation();
    }

    private static Animation animation;
    private static ImageView iv;
    private static int sum = 0;
    private static AnimationListener myAnimationListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            try {
                if (iv != null) {
                    int id = getImgId();
                    iv.setImageResource(id);
                }
            } catch (Exception e) {
                L.d(e.getMessage());
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        private int getImgId() {
            sum++;
            int s = sum % 8;
            if (sum == 800) {
                sum = 0;
            }
            int id = R.drawable.show_loading;
            switch (s) {
                case 0:
                    id = R.drawable.show_loading_1;
                    break;
                case 1:
                    id = R.drawable.show_loading_2;
                    break;
                case 2:
                    id = R.drawable.show_loading_3;
                    break;
                case 3:
                    id = R.drawable.show_loading_4;
                    break;
                case 4:
                    id = R.drawable.show_loading_5;
                    break;
                case 5:
                    id = R.drawable.show_loading_6;
                    break;
                case 6:
                    id = R.drawable.show_loading_7;
                    break;
                case 7:
                    id = R.drawable.show_loading_8;
                    break;
                default:
                    break;
            }
            return id;
        }
    };

    private static void setFlickerAnimation() {
        if (animation == null) {
            animation = new AlphaAnimation(1, 1); // 改变α从完全可见不可见
            animation.setDuration(100); // 时间——半秒
            animation.setInterpolator(new LinearInterpolator()); // 不改变动画的速度吗
            animation.setRepeatCount(Animation.INFINITE); // 重复动画无限
            animation.setRepeatMode(Animation.REVERSE); // 反向
            animation.setAnimationListener(myAnimationListener);
        }
        iv.setAnimation(animation);
    }

    /**
     * [Summary] setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public void setMessage(String strMessage) {
        TextView tvMsg = (TextView) dialogutil
                .findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
    }

    public void dismiss() {
        if (dialogutil != null) {
            super.dismiss();
        }
    }
}
