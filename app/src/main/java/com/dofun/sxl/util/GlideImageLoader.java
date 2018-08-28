package com.dofun.sxl.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dofun.sxl.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context).load(path).error(R.drawable.banner_main).placeholder(R.drawable.banner_main).into(imageView);
    }
}
