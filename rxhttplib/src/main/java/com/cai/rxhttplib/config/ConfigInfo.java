package com.cai.rxhttplib.config;

import com.cai.rxhttplib.callback.MyCallBack;

import java.util.HashMap;
import java.util.Map;

public class ConfigInfo {

    public int method;
    public String url;
    public Map params;
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

    public MyCallBack callBack;

    public void callBack() {

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

    public ConfigInfo setCodeSuccess(int code) {
        codeSuccess = code;
        return this;
    }
}
