package com.cai.rxhttplib;

import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.interfaces.HttpMethod;

public class HttpUtil {

    public static void init() {

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
}
