package com.dofun.sxl.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dofun.sxl.bean.BaseBean;
import com.tandong.sa.zUImageLoader.utils.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
    // private static SharedPreferences sp;// 都保存在这个sp中
    private static Context _this;
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "aboutus";

    // 手机信息
    public static final String IMEI = "imei"; // 手机IMEI号
    public static final String IMSI = "imsi";// 手机IESI号
    public static final String MTYPE = "mtype";// 手机型号
    public static final String MTYB = "mtyb";// 手机品牌
    public static final String PROVIDERSNAME = "ProvidersName"; // 运营商：
    public static final String NUMER = "numer"; // 手机号码
    public static final String RELEASE = "release";// 系统版本号
    public static final String IS_PHONE = "is_phone";// 是否有手机信息

    // apk信息
    public static final String VERSIONNAME = "versionName";// 版本名字
    public static final String VERSIONCODE = "versionCode";// 软件版本号
    public static final String APPNAME = "appName";// appName

    // token信息
    public static final String TOKEN = "token";
    public static final String USER = "user";// 用户信息
    public static final String TERM = "term";// 年级信息
    public static final String ISREG = "isReg";// 是否是注册进入首页，是时打开设置页面

    // 视频教学数据缓存
    public static final String SUBJECT = "subject";// 学科课程信息
    public static final String ISSUBJECT = "issubject";// 是否有修改学期，学科信息，视频教学中是否刷新数据
    public static final String MATERIALID = "MaterialIDSelected";// 选中的教材id
    public static final String ISPROMPT = "isprompt";//ar是否显示层
    public static final String ISSANP = "issanp";//是否显示引导页面
    public static final String ISLOGIN = "isLogin";//是否是自动登录 
    public static final String ISLOGIN2 = "isLogin2";//是否是由登录进入

    // 用户信息缓存
    public static final String UserName = "userName";
    public static final String UserPwd = "userPwd";

    // 播放选择 （使用2G/3G/4G网络播放）
    public static final String sPlay = "play_switch";
    // 下载选择 （使用2G/3G/4G网络下载）
    public static final String sDownload = "download_switch";

    //是否可以播放
    //    public static boolean isPlay() {
    //        try {
    //            UserInfoBean userInfo = (UserInfoBean) SPUtils.getBaseBean(SPUtils.USER, UserInfoBean.class);
    //            if (userInfo == null) {
    //                return false;
    //            }
    //            return userInfo.getMobileView().equals("1");
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return false;
    //    }

    //是否可以下载
    //    public static boolean isDownload() {
    //        try {
    //            UserInfoBean userInfo = (UserInfoBean) SPUtils.getBaseBean(SPUtils.USER, UserInfoBean.class);
    //            if (userInfo == null) {
    //                return false;
    //            }
    //            return userInfo.getMobileDown().equals("1");
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        return false;
    //    }

    //    //当前学科下，缓存的打开过的unityId
    //    public static void setLessonsOk(Integer subjectId, Integer unitsId) {
    //        SPUtils.setInt(MATERIALID + "_LessonsId_" + getTemrGrade() + "_" + subjectId, unitsId);
    //    }
    //
    //    public static int getLessonsOk(Integer subjectId) {
    //        return SPUtils.getInt(MATERIALID + "_LessonsId_" + getTemrGrade() + "_" + subjectId);
    //    }

    //    public static void setMATERIALID(Integer subjectId, MaterialBean material) {
    //        SPUtils.setBaseBean(MATERIALID + "_" + getTemrGrade() + "_" + subjectId,
    //                material);
    //        // SPUtils.put(MATERIALID + "_"+subjectId , MaterialID);
    //    }

    //    public static MaterialBean getMaterialID(Integer subjectId) {
    //        MaterialBean material = (MaterialBean) SPUtils.getBaseBean(
    //                MATERIALID + "_" + getTemrGrade() + "_" + subjectId,
    //                MaterialBean.class);
    //        return material;
    //    }
    //
    //    private static String getTemrGrade() {
    //        try {
    //            TermBean term = (TermBean) SPUtils.getBaseBean(SPUtils.TERM,
    //                    TermBean.class);
    //            return term.getGrade() + "_" + term.getId();
    //        } catch (Exception e) {
    //        }
    //        return "term";
    //    }

    /**
     * 设置sp
     *
     * @param context
     */
    public static void setSP(Context context) {
        // sp = PreferenceManager.getDefaultSharedPreferences(context);
        // sp = context.getSharedPreferences("washcar", Context.MODE_PRIVATE);
        _this = context;
        getInfo(context);
        getcode(context);
    }

    // 手机基本信息
    @SuppressLint("MissingPermission")
    private static void getInfo(Context context) {
        // if (!SPUtils.getBoolean(SPUtils.IS_PHONE)) {
        // return;
        // }
        // 有手机信息不再得到
        String imei = "", imsi = "", numer = "", mtype = "", mtyb = "",
                release = "", ProvidersName = "";
        try {
            @SuppressWarnings("static-access")
            TelephonyManager mTm = (TelephonyManager) context
                    .getSystemService(context.TELEPHONY_SERVICE);
            imei = mTm.getDeviceId();
            SPUtils.setString(SPUtils.IMEI, imei);
            imsi = mTm.getSubscriberId();
            SPUtils.setString(SPUtils.IMSI, imsi);
            numer = mTm.getLine1Number();// 手机号码，有的可得，有的不可得
            SPUtils.setString(SPUtils.NUMER, numer);
            mtype = android.os.Build.MODEL;// 手机型号
            SPUtils.setString(SPUtils.MTYPE, mtype);
            mtyb = android.os.Build.BRAND;// 手机品牌
            SPUtils.setString(SPUtils.MTYB, mtyb);
            release = android.os.Build.VERSION.RELEASE; // android系统版本号
            SPUtils.setString(SPUtils.RELEASE, release);
            ProvidersName = getprvidersname(imsi);// 运营商
            SPUtils.setString(SPUtils.PROVIDERSNAME, ProvidersName);
            SPUtils.setBoolean(SPUtils.IS_PHONE, true);// 手机信息设置完成
            // 保存 手机信息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // app信息
    private static void getcode(Context context) {
        try {
            PackageInfo pkg;
            pkg = context.getPackageManager().getPackageInfo(
                    context.getApplicationContext().getPackageName(), 0);
            String appName = pkg.applicationInfo
                    .loadLabel(context.getPackageManager()).toString();
            SPUtils.setString(SPUtils.APPNAME, appName);
            String versionName = pkg.versionName;
            SPUtils.setString(SPUtils.VERSIONNAME, versionName);
            int versionCode = pkg.versionCode;
            SPUtils.setInt(SPUtils.VERSIONCODE, versionCode);
        } catch (Exception e) {
        }
    }

    @SuppressLint("NewApi")
    private static String getprvidersname(String IMSI) {
        if (null == IMSI || IMSI.isEmpty()) {
            return "";
        }
        // 返回唯一的用户ID;就是这张卡的编号神马的
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            IMSI = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            IMSI = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            IMSI = "中国电信";
        }
        return IMSI;
    }

    public static String getString(String key, String defaultVal) {
        return (String) get(key, defaultVal);
    }

    public static boolean getBoolean(String key) {
        return (Boolean) get(key, false);
    }

    public static boolean getBoolean(String key, boolean bool) {
        return (Boolean) get(key, bool);
    }

    public static int getInt(String key) {
        return (Integer) get(key, 0);
    }

    public static int getInt(String key, int val) {
        return (Integer) get(key, val);
    }

    public static void setBoolean(String key, Boolean value) {
        put(key, value);
    }

    public static void setInt(String key, int value) {
        put(key, value);
    }

    public static void setString(String key, String value) {
        put(key, value);
    }

    public static void setJSONObject(String key, JSONObject value) {
        put(key, value);
    }

    public static JSONObject getJSONObject(String key) {
        return (JSONObject) get(key, new JSONObject());
    }

    public static void setBaseBean(String key, BaseBean bean) {
        if (bean != null) {
            put(key, bean);
        }
    }

    public static BaseBean getBaseBean(String key, Class _cls) {
        try {
            return (BaseBean) get(key, _cls.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    private static void put(String key, Object object) {
        if (object == null) {
            L.i("spUtils值 为空 key:" + key);
            return;
        }
        SharedPreferences sp = _this.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            editor.putString(key, jsonObject.toJSONString());
        } else if (object instanceof BaseBean) {
            String jsonString = JSON.toJSONString(object);
            editor.putString(key, jsonString);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    private static Object get(String key, Object defaultObject) {
        SharedPreferences sp = _this.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        try {
            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            } else if (defaultObject instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) defaultObject;
                return JSON.parseObject(
                        sp.getString(key, jsonObject.toJSONString()));
            } else if (defaultObject instanceof BaseBean) {
                String jsonString = sp.getString(key,
                        JSON.toJSONString(defaultObject));
                BaseBean parseObject = (BaseBean) JSON.parseObject(jsonString,
                        defaultObject.getClass());
                return parseObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultObject;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = _this.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear() {
        SharedPreferences sp = _this.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = _this.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = _this.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
