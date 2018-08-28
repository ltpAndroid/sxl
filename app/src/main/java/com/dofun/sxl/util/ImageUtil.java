package com.dofun.sxl.util;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.dofun.sxl.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author lulu
 * @Description: 关于图片处理文件生成工具类
 */
public class ImageUtil {

    public static Uri cropImageUri;

    /**
     * 临时list
     **/
    public static ArrayList<Uri> tmpImgUriList = new ArrayList<Uri>();


    public static File createFile(String fileName) {
        File file = null;
        File path1 = new File(getAppTempDir());

        if (!path1.exists()) {
            path1.mkdirs();
        }
        file = new File(path1, fileName);
        return file;
    }


    public static File writeToSDCard(Drawable d, File file) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            BitmapDrawable bd = (BitmapDrawable) d;
            Bitmap bm = bd.getBitmap();
            bm.compress(CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    /**
     * 对图片进行裁剪
     * @param activity
     * @param fromFile
     * @param size
     */
 /*   public static void startPhotoZoom(Activity activity,Uri fromFile, int size) {
   	 
   	 ImageUtil.cropImageUri = ImageUtil.createImagePathUri(activity);
   	 
   	 Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromFile, "image/*");
        intent.putExtra("crop", "true");
        //去黑边
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
   	 	intent.putExtra("noFaceDetection", true);
   	 	intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtil.cropImageUri);
   	 	System.out.println("ImageUtil.cropImageUri==========="+ImageUtil.cropImageUri);
   	 	//设置为不返回数据
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, ConstantValue.Photo_ZOOM);
   	}*/


    /**
     * 创建一条图片地址uri
     */
    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        //SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        //long time = System.currentTimeMillis();
        //String imageName = timeFormatter.format(new Date(time));
        //ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(1);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储

            imageFilePath = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        } else {
            imageFilePath = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        return imageFilePath;
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    public static Bitmap getBitmapUrl(String url) {
        File file = new File(url);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(url);


            int degree = readPictureDegree(file.getAbsolutePath());

            Bitmap newbitmap = rotaingImageView(degree, bitmap);


            return newbitmap;
        }
        return null;
    }


    public static Bitmap getDrawable(String pathName, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bmp = null;
        try {
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            BitmapFactory.decodeFile(pathName, opts);
            opts.inSampleSize = computeSampleSize(opts, -1, 800 * 800);
            opts.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeFile(pathName, opts);
            bmp = scaleImg(bmp, w, h);
        } catch (OutOfMemoryError e) {
            bmp = scaleImg(bmp, w, h);
        }
        return bmp;
    }

    /**
     * 动态计算出图片的inSampleSize
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return 返回图片的最佳inSampleSize
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 等比压缩图片
     *
     * @param bm        被压缩的图片
     * @param newWidth  压缩后的宽度
     * @param newHeight 压缩后的图片高度
     * @return 一张新的图片
     * @author
     */

    public static Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {
        // 图片源
        // Bitmap bm = BitmapFactory.decodeStream(getResources()
        // .openRawResource(id));
        // 获得图片的宽高
        // Bitmap newbm = null;
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        float newWidth1 = newWidth;
        float newHeight1 = newHeight;
        /*
         * if(width<newWidth1 && height <newHeight1){ return bm; }
         */
        // 计算缩放比例
        if (height > width) {
            float scaleHeight = ((float) newHeight1) / height;
            newWidth1 = ((float) (width * (float) newHeight1) / height);
            float scaleWidth = ((float) newWidth1) / width;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            Bitmap scaleBit = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
            return scaleBit;
        } else {
            float scaleWidth = ((float) newWidth1) / width;
            newHeight1 = ((float) (height * (float) newWidth1) / width);
            float scaleHeight = ((float) newHeight1) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片

            Bitmap scaleBit = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
            // Bitmap bmp = scaleBit;
            return scaleBit;
        }

    }

    // 放大图片
    public static Bitmap Enlarge(Bitmap bitmap, double scale) {
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        float scaleWidth = 1;
        float scaleHeight = 1;
        /* 计算这次要放大的比例 */
        scaleWidth = (float) (scaleWidth * scale);
        scaleHeight = (float) (scaleHeight * scale);
        Bitmap resizeBmp = null;
        try {
            /* 产生reSize后的Bitmap对象 */
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
        } catch (OutOfMemoryError e) {
            if (!resizeBmp.isRecycled()) {
                resizeBmp.recycle();
            }
            System.gc();
        } catch (Exception e) {
        }
        return resizeBmp;
    }

    /**
     * 通过传入位图,新的宽.高比进行位图的缩放操作
     */
    public static Bitmap BitmapZoom(Bitmap bitmap, int w, int h) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        try {
            if (width > w || height > h) {
                Matrix matrix = new Matrix();
                if (width > w) {
                    // 计算缩放比
                    float scaleWidht = ((float) w / width);
                    // 缩放操作
                    matrix.postScale(scaleWidht, scaleWidht);
                    Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                    return newbmp;
                } else {
                    return bitmap;
                }
            } else if (width < w && height < h) {
                // 取当前bitmap对象的宽高与屏幕的宽高的比例取最小值进行放大操作
                return Enlarge(bitmap, (float) w / width < (float) h / height ? (float) w / width : (float) h / height);
            } else if (width == h || height == h) {
                return bitmap;
            }
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmapByFile(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    public static void compressImageToFile(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int options = 70;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void compressFile(File file) {
        Bitmap bp = getBitmapByFile(file);
        compressImageToFile(bp, file);
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;

                fis = new FileInputStream(file);
                size = fis.available();


            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取app的根目录
     *
     * @return
     */
    public static String getAppRootDir() {

        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = MyApplication.getMyApp().getExternalCacheDir().getPath();
        } else {
            cachePath = MyApplication.getMyApp().getCacheDir().getPath();
        }

        File temp = new File(cachePath);
        if (!temp.exists()) {
            temp.mkdirs();
        }

        return temp.getAbsolutePath();
    }

    /**
     * 获取app保存图片的临时路径
     *
     * @return
     */
    public static String getAppTempDir() {

        File file = new File(getAppRootDir() + File.separator + "image_cache");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }

}
