package com.dofun.sxl.http;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.MyApplication;
import com.dofun.sxl.http.httputil.HttpException;
import com.dofun.sxl.http.httputil.HttpHandler;
import com.dofun.sxl.http.httputil.HttpRequest;
import com.dofun.sxl.http.httputil.RequestCallBack;
import com.dofun.sxl.http.httputil.RequestParams;
import com.dofun.sxl.http.httputil.ResponseInfo;
import com.dofun.sxl.util.SPUtils;

public class HttpUs {
    // 网络 回调方法
    public interface CallBackImp {
        public void onSuccess(ResInfo info);

        public void onFailure(ResInfo info);
    }

    /**
     * 访问网络方法重载 post方法
     *
     * @param method
     * @param params
     * @param _imp
     */
    public static void send(String method[], JSONObject params, CallBackImp _imp) {
        send(method, params, _imp, null, null);
    }

    public static void send(String method[], JSONObject params, CallBackImp _imp,
                            Activity contex) {
        send(method, params, _imp, contex, null);
    }

    /**
     * 网络访问方法
     *
     * @param method
     * @param params
     * @param _imp
     * @param _act
     * @param msg
     */
    public static void send(String method[], JSONObject params,
                            CallBackImp _imp, Context _act, String msg) {

        if (params == null) {
            params = new JSONObject();
        }
        HttpUtils http = new HttpUtils();
        RequestParams data = new RequestParams();

        JSONObject json = new JSONObject();
        json.put("deviceId", SPUtils.getString(SPUtils.IMEI, ""));
        json.put("method", method[1]);// 调用方法
        json.put("token", SPUtils.getString(SPUtils.TOKEN, ""));// token,登录后就使用
        json.put("params", params);// 方法的参数
        LogUtils.i("网络参数:" + json.toJSONString());
        data.addBodyParameter("data", json.toJSONString());
        MyCallBack newInstance = MyCallBack.newInstance(_imp, _act, msg);
        HttpHandler<String> handler = http.send(HttpRequest.HttpMethod.POST, Deploy.URL + method[0], data, newInstance);
        //设置网络管理类
        newInstance.setHandler(handler);
    }
    //   同步网络方法
    //	public static ResponseStream sendSync(String method[], JSONObject params) throws HttpException {
    //
    //		if (params == null) {
    //			params = new JSONObject();
    //		}
    //		HttpUtils http = new HttpUtils();
    //		RequestParams data = new RequestParams();
    //
    //		JSONObject json = new JSONObject();
    //		json.put("deviceId", SPUtils.getString(SPUtils.IMEI));
    //		json.put("method", method[1]);// 调用方法
    //		json.put("token",SPUtils.getString(SPUtils.TOKEN));// token,登录后就使用
    //		json.put("params", params);// 方法的参数
    //		L.iH("网络参数:" + json.toJSONString());
    //		data.addBodyParameter("data", json.toJSONString());
    //		return http.sendSync(HttpMethod.POST, Deploy.URL + method[0], data);
    //	}
}

// 网络进度控制
class MyCallBack extends RequestCallBack<String> {
    private HttpUs.CallBackImp _RequestCallBackImp;
    // 进度的显示
    private DialogUtil dialog;
    private HttpHandler<String> handler;

    public void setHandler(HttpHandler<String> handler) {
        this.handler = handler;
    }

    public static MyCallBack newInstance(HttpUs.CallBackImp _RequestCallBackImp,
                                         Context _act, String msg) {
        return new MyCallBack(_RequestCallBackImp, _act, msg);
    }

    private MyCallBack(HttpUs.CallBackImp _RequestCallBackImp, Context _act,
                       String msg) {
        this._RequestCallBackImp = _RequestCallBackImp;
        // 控制进度显示或内容
        if (_act != null) {
            dialog = DialogUtil.createDialog(_act);
            if (msg != null) {
                dialog.setMessage(msg);
            }
            dialog.show();
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (handler != null) {
                            dismiss();
                            handler.cancel(true);
                        }
                    }
                    return false;
                }
            });
        }
    }

    public void onFailure(HttpException arg0, String arg1) {
        LogUtils.i("网络失败:" + arg1);
        dismiss();
        try {
            if (arg1.endsWith("No address associated with hostname")) {
                arg1 = "请检查网络";
            } else if (arg1.endsWith("refused")) {
                //org.apache.http.conn.HttpHostConnectException: Connection to http://app.aoshiapp.com refused
                arg1 = "连接拒绝";
            }
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arg1 = "网络连接错误,请检查网络.";
            if (_RequestCallBackImp != null) {
                ResInfo info = new ResInfo();
                info.setStatus("1000");
                info.setMsg(arg1);
                _RequestCallBackImp.onFailure(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSuccess(ResponseInfo<String> arg0) {
        LogUtils.i("网络成功:" + arg0.result);
        dismiss();
        try {
            if (_RequestCallBackImp != null) {
                ResInfo info = JSON.parseObject(arg0.result, ResInfo.class);
                if (info.getStatus().equals("200")) {
                    _RequestCallBackImp.onSuccess(info);
                } else if (info.getStatus().equals("404")) {
                    //退出登录，当前用户未登录
                    //                    SPUtils.setString(SPUtils.UserName, "");
                    //                    SPUtils.setString(SPUtils.UserPwd, "");
                    //                    // 停止账号统计
                    //                    MobclickAgent.onProfileSignOff();
                    MyApplication.exitApp();
                    MyApplication.toLogin();
                    ToastUtils.showShort(info.getMsg());
                } else {
                    _RequestCallBackImp.onFailure(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 关进度条
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
