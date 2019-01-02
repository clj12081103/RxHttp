package com.cai.rxhttplib.config;

public class GlobalConfig {

    public final static String baseUrl = "";

    public final static long TIME_OUT = 20 * 1000;//单位为ms,默认15s
    public final static int RETRY_COUNT = 0;

    public final static String KEY_TOKEN = "auth_token";
    public final static String KEY_DATA = "data";
    public final static String KEY_CODE = "code";
    public final static String KEY_MSG = "message";

    public final static int CODE_SUCCESS = 200;
    public final static int CODE_2003 = 2003;
    public final static int CODE_5108 = 5108;//强制升级


    public static boolean isOpenLog = true;
}
