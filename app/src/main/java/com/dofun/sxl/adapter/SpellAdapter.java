package com.dofun.sxl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dofun.sxl.R;

import java.util.List;

public class SpellAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SpellAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_spell_word, item)
                .addOnClickListener(R.id.item_spell_word);
    }
}
