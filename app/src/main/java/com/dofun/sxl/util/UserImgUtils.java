package com.dofun.sxl.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 用户头像上传工具类
 */
public class UserImgUtils {

    //根据uri得到btimap
    public static Bitmap getBitmapFromUri(Context _this, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(_this.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据bitmap得到base64String
    public static String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    //保存图片
    public static boolean saveScreenshot(Bitmap bitmap, String path) {
        FileOutputStream b = null;
        try {
            b = new FileOutputStream(path);
            bitmap.compress(CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            System.err.println("getBitmapFromPath: file not exists");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {

            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err
                        .println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return bitmap;
    }

    // // 剪裁图片方法--------------------------------------
    // //startPhotoZoom(Uri.fromFile(new File(path)), 150); 相册选择
    // //startPhotoZoom(Uri.fromFile(new File(path)), 150); 拍照
    // // 将进行剪裁后的图片显示到UI界面上
    // private void setPicToView(Intent picdata) {
    // Bundle bundle = picdata.getExtras();
    // if (bundle != null) {
    // Bitmap photo = bundle.getParcelable("data");
    // Bitmap roundBitmap = roundedCornerBitmap(photo, 360);
    // confirm_camera(roundBitmap);
    // }
    // }
    // public static Bitmap roundedCornerBitmap(Bitmap bitmap, float roundPx) {
    // Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
    // bitmap.getHeight(), Config.ARGB_8888);
    // Canvas canvas = new Canvas(output);
    // final int color = 0xff424242;
    // final Paint paint = new Paint();
    // final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    // final RectF rectF = new RectF(rect);
    // paint.setAntiAlias(true);
    // canvas.drawARGB(0, 0, 0, 0);
    // paint.setColor(color);
    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    // paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    // canvas.drawBitmap(bitmap, rect, rect, paint);
    // return output;
    // }
    // private void startPhotoZoom(Uri uri, int size) {
    // Intent intent = new Intent("com.android.camera.action.CROP");
    // intent.setDataAndType(uri, "image/*");
    // // crop为true是设置在开启的intent中设置显示的view可以剪裁
    // intent.putExtra("crop", "true");
    //
    // // aspectX aspectY 是宽高的比例
    // intent.putExtra("aspectX", 1);
    // intent.putExtra("aspectY", 1);
    //
    // // outputX,outputY 是剪裁图片的宽高
    // intent.putExtra("outputX", size);
    // intent.putExtra("outputY", size);
    // intent.putExtra("return-data", true);
    // startActivityForResult(intent, PHOTO_REQUEST_CUT);
    // }
}
