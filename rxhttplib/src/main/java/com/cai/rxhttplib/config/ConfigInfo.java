package com.cai.rxhttplib.config;

import com.cai.rxhttplib.Runner;
import com.cai.rxhttplib.callback.MyCallBack;
import com.cai.rxhttplib.convert.Converter;
import io.reactivex.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigInfo<T> {

    public int method;
    public String url;
    public Map params;
    public Map<String, String> headerParams = new HashMap<>();

    //上传的文件路径
    public Map<String, String> files;
    public Map<String, List<String>> files2;//一个key接收多个文件

    public int type;

    public static final int TYPE_STRING = 1;//純文本,比如html
    public static final int TYPE_JSON = 2;
    public static final int TYPE_JSON_FORMATTED = 3;//jsonObject包含data,code,msg,數據全在data中,可能是obj,頁可能是array,也可能為空

    public static final int TYPE_DOWNLOAD = 4;
    public static final int TYPE_UPLOAD_WITH_PROGRESS = 5;

    //设置标准格式json本次响应的不同字段
    public String keyCode = "";
    public String keyData = "";
    public String keyMSg = "";
    public int codeSuccess;
    public int codeUnlogin;

    public Converter<T> converter;

    //是否加密
    public boolean isEncrypt = true;
    //加密key
    public String encryptKey;
    //是否显示错误信息
    public boolean showErrorMsg = true;
    //是否跳转到登录页
    public boolean toLoginActivity = true;
    //是否拼接token
    public boolean isAppendToken = true;

    public boolean isParamsAsJson = false;

    public boolean download;
    public boolean uploadMultipart;
    public boolean uploadBinary;

    public MyCallBack<T> callBack;

    public void callBack(MyCallBack<T> callBack) {
        this.callBack = callBack;
        Runner.asCallback(this);
    }

    /**
     * 使用Observable模式，需要添加一个{@link Converter}用于数据解析
     * 使用callBack模式不需要
     *
     * @return Observable
     */
    public Observable<T> asObservable() {
        return Runner.asObservable(this);
    }

    public ConfigInfo addParams(String key, Object value) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        if (value == null) {
            params.put(key, value);
        } else {
            params.put(key, value.toString());
        }
        return this;
    }

    /**
     * 添加文件
     * 一般用于上传文件,一个key对应一个文件
     *
     * @param key
     * @param path
     * @return
     */
    public ConfigInfo addFile(String key, String path) {
        if (files == null) {
            files = new HashMap<>();
        }
        files.put(key, path);
        return this;
    }

    public ConfigInfo setConverter(Converter<T> converter) {
        this.converter = converter;
        return this;
    }

    public boolean isShowErrorMsg() {
        return showErrorMsg;
    }

    public ConfigInfo setShowErrorMsg(boolean showErrorMsg) {
        this.showErrorMsg = showErrorMsg;
        return this;
    }

    public boolean isToLoginActivity() {
        return toLoginActivity;
    }

    public ConfigInfo setToLoginActivity(boolean toLoginActivity) {
        this.toLoginActivity = toLoginActivity;
        return this;
    }

    public boolean isAppendToken() {
        return isAppendToken;
    }

    public ConfigInfo setAppendToken(boolean appendToken) {
        isAppendToken = appendToken;
        return this;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public ConfigInfo setEncrypt(boolean encrypt) {
        isEncrypt = encrypt;
        return this;
    }

    public ConfigInfo setDataFormatKey(String keyCode, String keyData, String keyMSg) {
        this.keyCode = keyCode;
        this.keyData = keyData;
        this.keyMSg = keyMSg;
        return this;
    }

    public ConfigInfo setCodeUnlogin(int code) {
        codeUnlogin = code;
        return this;
    }

    public boolean isCustomCodeSuccess;

    public ConfigInfo setCodeSuccess(int code) {
        codeSuccess = code;
        isCustomCodeSuccess = true;
        return this;
    }
}
