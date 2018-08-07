package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.ListBean;

import java.util.List;

public class ListBeanAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder> {
    public ListBeanAdapter(int layoutResId, @Nullable List<ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        String value = item.getValue();
        String type = item.getType();
        int iconId = 0;
        if (type.equals("question_types")) {
            switch (value) {
                case "填空":
                    value = setValueName(R.string.jwth);
                    iconId = R.drawable.jwth;
                    break;
                case "连线":
                    value = setValueName(R.string.sckl);
                    iconId = R.drawable.sckl;
                    break;
                case "拼诗":
                    value = setValueName(R.string.ggxs);
                    iconId = R.drawable.ggxs;
                    break;
                case "选择":
                    value = setValueName(R.string.sqxm);
                    iconId = R.drawable.sqxm;
                    break;
                case "判断":
                    value = setValueName(R.string.mbsf);
                    iconId = R.drawable.mbsf;
                    break;
                case "诵读":
                    value = setValueName(R.string.ryss);
                    iconId = R.drawable.ryss;
                    break;
                case "视频":
                    value = setValueName(R.string.syzy);
                    iconId = R.drawable.syzy;
                    break;
            }
        } else if (type.equals("operation_type")) {
            switch (helper.getLayoutPosition()) {
                case 0:
                    iconId = R.drawable.num_001;
                    break;
                case 1:
                    iconId = R.drawable.num_002;
                    break;
                case 2:
                    iconId = R.drawable.num_003;
                    break;
                case 3:
                    iconId = R.drawable.num_004;
                    break;
                case 4:
                    iconId = R.drawable.num_005;
                    break;
                case 5:
                    iconId = R.drawable.num_006;
                    break;
                case 6:
                    iconId = R.drawable.num_007;
                    break;
            }

            //            switch (value) {
            //                case "填空":
            //                    value = setValueName(R.string.lys_tk);
            //                    break;
            //                case "连线":
            //                    value = setValueName(R.string.lys_lx);
            //                    break;
            //                case "判断":
            //                    value = setValueName(R.string.lys_pd);
            //                    break;
            //                case "选择":
            //                    value = setValueName(R.string.lys_xz);
            //                    break;
            //                case "计算题":
            //                    value = setValueName(R.string.lys_js);
            //                    break;
            //                case "应用题":
            //                    value = setValueName(R.string.lys_yy);
            //                    break;
            //                case "画图题":
            //                    value = setValueName(R.string.lys_ht);
            //                    break;
            //            }
        }

        helper.setText(R.id.item_sjd_name, value);
        Glide.with(mContext).load(iconId).into((ImageView) helper.getView(R.id.item_sjd_icon));
    }

    private String setValueName(int resId) {
        return mContext.getResources().getString(resId);
    }
}
