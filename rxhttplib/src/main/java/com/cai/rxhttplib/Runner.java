package com.cai.rxhttplib;

import com.blankj.utilcode.util.AppUtils;
import com.blocain.bihu.apps.sdk.internal.util.Encrypt;
import com.blocain.bihu.apps.sdk.internal.util.Signature;
import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.config.GlobalConfig;
import com.cai.rxhttplib.utils.RSAUtils;
import com.cai.rxhttplib.utils.ResponseTransformer;
import com.cai.rxhttplib.utils.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Runner {

    public static <T> void asCallback(ConfigInfo<T> info) {
        Observable<T> observable = asObservable(info);
//        if (!info.isSync()) {
//            observable =   observable.observeOn(SchedulerProvider.getInstance().ui());
//        }
        observable.subscribe(info.callBack);
    }


    public static <T> Observable<T> asObservable(ConfigInfo<T> configInfo) {

        if (configInfo.isAppendToken) {
            configInfo.params.put(GlobalConfig.KEY_TOKEN,GlobalConfig.getToken());
        }
        //添加公共参数并加密签名
        addCommonParamsAndEncrypt(configInfo);

        if (configInfo.download) {

            return null;
        } else if (configInfo.uploadBinary || configInfo.uploadMultipart) {
            return null;
        } else {
            return handleRequest(configInfo);
        }
    }

    private static <T> void addCommonParamsAndEncrypt(ConfigInfo<T> configInfo) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String content = gson.toJson(configInfo.params);
        Map<String,String> treeMap = new TreeMap<>();
        Map<String,String> finalMap = new TreeMap<>();

        if (configInfo.isEncrypt) {
            configInfo.encryptKey = timestamp;
            content = Encrypt.encryptContent(content, "AES", configInfo.encryptKey);
            treeMap.put("encrypt_key", timestamp);
            finalMap.put("encrypt_key", timestamp);
        }

        finalMap.put("format", "json");
        finalMap.put("charset", "UTF-8");
        finalMap.put("sign_type", "RSA2");
        finalMap.put("deviceType", "android");
        finalMap.put("appVersion", AppUtils.getAppVersionName());
        finalMap.put("version", "1.0");
        finalMap.put("timestamp", timestamp);
        finalMap.put("content", content);


        treeMap.put("format", "json");
        treeMap.put("charset", "UTF-8");
        treeMap.put("sign_type", "RSA2");
        treeMap.put("deviceType","android");
        treeMap.put("appVersion", AppUtils.getAppVersionName());
        treeMap.put("version", "1.0");
        treeMap.put("timestamp", timestamp);
        treeMap.put("content", content);

        String signContent = Signature.getSignContent(treeMap);

        String rsaSign = null;
        try {
            rsaSign = RSAUtils.sign(signContent.getBytes(), Constans.rsaPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finalMap.put("sign", rsaSign);
        configInfo.params = finalMap;
    }

    private static <T> Observable<T> handleRequest(ConfigInfo<T> info) {
        Observable<ResponseBody> observable = RetrofitHelper.getResponseObservable(info);
        observable = observable.subscribeOn(SchedulerProvider.getInstance().io());
        return observable.compose(ResponseTransformer.handleResult(info))
                .timeout(GlobalConfig.TIME_OUT, TimeUnit.MILLISECONDS)
                .retry(GlobalConfig.RETRY_COUNT);

    }


}
