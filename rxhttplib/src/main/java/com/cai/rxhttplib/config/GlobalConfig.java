package com.cai.rxhttplib.config;

import com.cai.rxhttplib.interfaces.ITokenManager;

public class GlobalConfig {

    final public static String baseUrl = "http://192.168.31.41:8087/";

    public static int TIME_OUT = 20 * 1000;//单位为ms,默认15s
    public static int RETRY_COUNT = 0;

    public static String KEY_TOKEN = "auth_token";
    public static String KEY_DATA = "data";
    public static String KEY_CODE = "code";
    public static String KEY_MSG = "message";

    public static int CODE_SUCCESS = 200;

    public static long PROGRESS_INTERVAL = 100;//进度条更新间隔,默认100ms

    public static ITokenManager mTokenManager;

    public static String getToken() {
        return mTokenManager.getToken();
    }
}
