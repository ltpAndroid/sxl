package com.dofun.sxl.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.dofun.sxl.R;


public class HintDiaUtils extends Dialog {
    private Context context;
    private ImageView hint_img;
    private TextView hint_text;

    private HintDiaUtils(Context context) {
        super(context);
        this.context = context;
    }

    private HintDiaUtils(Context context, int theme) {
        super(context, theme);
        this.context = context;

        View view = View.inflate(context, R.layout.hint_dialog, null);
        this.setContentView(view);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        //this.setCancelable(false);// 点击外面不会自动消失
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
        hint_img = (ImageView) view.findViewById(R.id.hint_img);
        hint_text = (TextView) view.findViewById(R.id.hint_text);
    }

    /*
     * 初始代
     */
    public static HintDiaUtils createDialog(Context context) {
        HintDiaUtils dialogutil = new HintDiaUtils(context, R.style.CustomDialogHint);
        return dialogutil;
    }

    /**
     * 发送成功按钮
     */
    public void showSucceedDialog(String text) {
        showDialog(text, R.drawable.succeed_img);
    }

    /**
     * 发送显示
     *
     * @param text
     * @param img
     */
    public void showDialog(String text, int img) {
        if (!StringUtils.isEmpty(text)) {
            hint_text.setText(text);
        }
        hint_img.setImageResource(img);
        show();
    }

    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }
}
