package com.cai.rxhttplib.utils;

import com.blankj.utilcode.util.SPUtils;
import com.cai.rxhttplib.RxHttp;
import com.cai.rxhttplib.callback.BaseSubscriber;
import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.config.GlobalConfig;
import com.cai.rxhttplib.exceptions.CodeErrorException;

public class ExceptionDispatcher {

    public static void dispatchException(Throwable e, BaseSubscriber baseSubscriber) {

        if (e instanceof CodeErrorException) {
            parseErrorCode((CodeErrorException) e, baseSubscriber);
        }
    }

    private static void parseErrorCode(CodeErrorException e, BaseSubscriber baseSubscriber) {
        int code = e.code;
        String data = e.data;
        String msg = e.msg;
        ConfigInfo configInfo = e.configInfo;

        if ((code == GlobalConfig.CODE_2003 || code == 2004 || code == 2006)) {
            if (code == 2006) {
                SPUtils.getInstance().put("token", "");
                SPUtils.getInstance().put("phone", "");
                SPUtils.getInstance("user_info").put("unid", "");
            }
            if (code == GlobalConfig.CODE_2003) {
                configInfo.setShowErrorMsg(false);
            }
            if (configInfo.toLoginActivity) {
                RxHttp.autoLogin();
            }
            baseSubscriber.onError(msg, code, data, configInfo);
        } else if (code == GlobalConfig.CODE_5108) {
            //强制升级
            RxHttp.updateApp();
            configInfo.showErrorMsg = true;
            baseSubscriber.onError(msg, code, data, configInfo);
        } else {
            baseSubscriber.onError(msg, code, data, configInfo);
        }
    }
}
