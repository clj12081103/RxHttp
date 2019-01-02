package com.cai.rxhttplib;

import android.os.Looper;
import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.config.GlobalConfig;
import com.cai.rxhttplib.utils.EncryptUtil;
import com.cai.rxhttplib.utils.ResponseTransformer;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Runner {

    public static <T> void asCallback(ConfigInfo<T> info) {
        Observable<T> observable = asObservable(info);
        observable.subscribe(info.callBack);
    }


    public static <T> Observable<T> asObservable(ConfigInfo<T> configInfo) {

        if (configInfo.isAppendToken) {
            configInfo.addParams(GlobalConfig.KEY_TOKEN, RxHttp.getToken());
        }
        //添加公共参数并加密签名
        EncryptUtil.addCommonParamsAndEncrypt(configInfo);

        if (configInfo.download) {

            return null;
        } else if (configInfo.uploadBinary || configInfo.uploadMultipart) {
            return RetrofitHelper.getResponseObservable(configInfo)
                    .subscribeOn(Schedulers.io())
                    .compose(ResponseTransformer.handleResult(configInfo))
                    .timeout(GlobalConfig.TIME_OUT, TimeUnit.MILLISECONDS)
                    .retry(GlobalConfig.RETRY_COUNT);

        } else {
            return handleRequest(configInfo);
        }
    }


    private static <T> Observable<T> handleRequest(ConfigInfo<T> info) {
        return RetrofitHelper.getResponseObservable(info)
                .subscribeOn(Schedulers.io())
                .compose(ResponseTransformer.handleResult(info))
                .timeout(GlobalConfig.TIME_OUT, TimeUnit.MILLISECONDS)
                .retry(GlobalConfig.RETRY_COUNT);
    }

    private static android.os.Handler mainHandler;

    public static void runOnUI(Runnable runnable) {
        if (mainHandler == null) {
            mainHandler = new android.os.Handler(Looper.getMainLooper());
        }
        mainHandler.post(runnable);
    }
}
