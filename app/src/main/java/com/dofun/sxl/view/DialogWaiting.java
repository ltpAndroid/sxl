package com.dofun.sxl.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.dofun.sxl.R;


public class DialogWaiting extends Dialog {
    private TextView title;
    private ProgressBar img;
    private AnimationDrawable anim;

    private DialogWaiting(Context context) {
        this(context, SizeUtils.dp2px(120), SizeUtils.dp2px(80));
    }

    private DialogWaiting(Context context, int width, int height) {
        super(context, R.style.dialog1);
        // set content
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        title = layout.findViewById(R.id.progress_dialog_content);
        img = layout.findViewById(R.id.progressBar1);
        setContentView(layout);
        // set window params
        Window window = this.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = SizeUtils.dp2px(60);
        params.height = SizeUtils.dp2px(60);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

    }

    public static DialogWaiting build(Context context) {
        return new DialogWaiting(context);
    }

    public static DialogWaiting show(Context context) {
        DialogWaiting progress = new DialogWaiting(context);
        progress.setCanceledOnTouchOutside(false);
        progress.title.setText("请 稍 候");
        progress.show();
        return progress;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }

    public static DialogWaiting show(Context context, CharSequence message) {
        DialogWaiting progress = new DialogWaiting(context);
        ((TextView) progress.findViewById(R.id.progress_dialog_content)).setText(message);
        progress.show();
        return progress;
    }

    public void dimiss() {
        this.cancel();
    }


    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }
}
