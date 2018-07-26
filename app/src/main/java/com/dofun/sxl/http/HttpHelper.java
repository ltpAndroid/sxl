package com.dofun.sxl.http;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.util.Des3Util;
import com.dofun.sxl.view.DialogWaiting;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;
import com.tandong.sa.zUImageLoader.utils.L;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class HttpHelper {
    /**
     * 上下文
     */
    private Context mContext;
    private DialogWaiting waiting;

    /**
     * 返回值的json
     **/
    private JSONObject requestJson;

    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    static {
        httpClient.setTimeout(50000);
    }

    /**
     * @param cxt       上下文
     * @param params    json请求
     * @param urlStr    请求连接关键字
     * @param code1     区分网络code
     * @param isLoading 是否有加载框
     * @param callback  回调函数
     */
    public void networkHelper(String urlStr, JSONObject params, final int code1, final Boolean isLoading, final HttpCallback callback, Context cxt) {

        this.mContext = cxt;
        L.i("params>>>>>>>>>>>>>" + urlStr + params.toString());
        final RequestParams reparams = createParams(params);


        final String requstUrl = CommonURL.BASE_URL + File.separator + urlStr;
        L.i("params>>>>>>>>>>>>>" + requstUrl + "--------");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Log.e("requstUrl------------", requstUrl);
                httpClient.post(requstUrl, reparams, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {

                        if (mContext != null && isLoading) {

                            waiting = DialogWaiting.show(mContext);
                        }
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] responseBody) {

                        dismissDialog();

                        try {

                            String result = new String(responseBody);

                            requestJson = new JSONObject(result);

                            Log.e("ResponseJson==", requestJson.toString());

                            if ("0".equals(requestJson.optString("status")) || "".equals(requestJson.optString("status"))) {

                                callback.onSuccess(requestJson, code1); //返回成功状态值为0

                            } else {
                                callback.onSucessData(code1);
                                onLoadDataSuccFailed(requestJson); //返回错误码
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

                        System.out.println("onRequetFailed" + arg0 + "" + arg3);

                        dismissDialog();
                        callback.onFailure(code1);
                        onRequetFailed();
                    }

                });
            }
        }, 0);

    }

    public HttpCallback mCallback;


    public interface HttpCallback {
        void onSuccess(JSONObject result, int code) throws JSONException;

        void onFailure(int code);

        /**
         * 请求成功,错误码类型
         */
        void onSucessData(int code);
    }


    /**
     * 创建请求参数
     *
     * @param joParams
     * @return
     */
    private RequestParams createParams(JSONObject joParams) {

        if (joParams != null) {
            RequestParams params = new RequestParams();
            params.put("biz", Des3Util.encodeDES(joParams.toString()));
            return params;
        }
        return null;
    }


    /**
     * 请求成功，获取数据失败，回调该方法
     * <p/>
     * 加载数据失败
     *
     * @param joResponse 服务器返回所有数据
     */
    public void onLoadDataSuccFailed(JSONObject joResponse) throws JSONException {

        if (joResponse.getString("status").equals("")) {
            return;
        }
        int status = joResponse.getInt("status");
        processStatusCode(status);
    }


    /**
     * 请求数据失败，回调该方法
     * <p/>
     * 连接超时或服务地址错误时，回调该方法
     */
    public void onRequetFailed() {
        ToastUtils.showShort(mContext.getResources().getString(R.string.network_request_fail));
    }


    private boolean isFinishing() {
        return ((Activity) mContext).isFinishing();
    }


    protected void dismissDialog() {
        if (waiting != null && waiting.isShowing() && !isFinishing()) {
            waiting.dismiss();
        }
    }


    /**
     * 取消 正在请求或等待请求连接的请求
     *
     * @param cxt
     * @param mayInterruptIfRunning
     */
    public void cancelRequests(Context cxt, boolean mayInterruptIfRunning) {

        httpClient.cancelRequests(cxt, mayInterruptIfRunning);
    }

    /**
     * @param responseString
     * @return 返回true
     * @throws JSONException
     */
    protected boolean onRequestSuccessBeCall(String responseString) {

        return false;
    }


    /**
     * 处理服务返回状态码
     *
     * @param status
     */
    protected void processStatusCode(int status) {

        switch (status) {

        }
    }

}
