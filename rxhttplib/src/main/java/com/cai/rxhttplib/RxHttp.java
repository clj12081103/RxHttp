package com.cai.rxhttplib;

import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.config.GlobalConfig;
import com.cai.rxhttplib.interfaces.HttpMethod;
import com.cai.rxhttplib.interfaces.INetManager;

public class RxHttp {

    private static INetManager netManager;

    public static void init(boolean isOpenLog, INetManager manager) {
        GlobalConfig.isOpenLog = isOpenLog;
        netManager = manager;
    }

    public static ConfigInfo postFormat(String url) {
        ConfigInfo config = new ConfigInfo();
        config.url = url;
        config.method = HttpMethod.POST;
        return config;
    }

    public static ConfigInfo getFormat(String url) {
        ConfigInfo config = new ConfigInfo();
        config.url = url;
        config.method = HttpMethod.GET;
        return config;
    }

    public static ConfigInfo uploadMultipart(String url) {
        ConfigInfo config = new ConfigInfo();
        config.url = url;
        config.method = HttpMethod.POST;
        config.uploadMultipart = true;
        return config;
    }

    public static void autoLogin() {
        if (netManager != null) {
            netManager.autoLogin();
        }
    }

    public static void isLogin() {
        if (netManager != null) {
            netManager.isLogin();
        }
    }

    public static void updateApp() {
        if (netManager != null) {
            netManager.updateApp();
        }
    }

    public static String getToken() {
        if (netManager != null) {
            return netManager.getToken();
        }
        return "";
    }

    public static void addCommonParamsAndEncrypt(ConfigInfo configInfo) {
        if (netManager != null) {
            netManager.addCommonParamsAndEncrypt(configInfo);
        }
    }
}
