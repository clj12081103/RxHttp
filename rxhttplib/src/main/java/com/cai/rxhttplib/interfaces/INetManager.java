package com.cai.rxhttplib.interfaces;


import com.cai.rxhttplib.config.ConfigInfo;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public interface INetManager {

    boolean isLogin();

    void autoLogin();

    void updateApp();

    String getToken();

    void addCommonParamsAndEncrypt(ConfigInfo configInfo);

}
