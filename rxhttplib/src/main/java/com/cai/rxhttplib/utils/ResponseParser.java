package com.cai.rxhttplib.utils;

import android.text.TextUtils;
import com.blankj.utilcode.util.LogUtils;
import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.config.GlobalConfig;
import com.cai.rxhttplib.exceptions.CodeErrorException;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ResponseParser {

    public static <T> T parseResponse(String responseStr, ConfigInfo<T> configInfo) throws Exception {

        JSONObject object = new JSONObject(responseStr);

        String key_data = TextUtils.isEmpty(configInfo.keyData) ? GlobalConfig.KEY_DATA : configInfo.keyData;
        String key_code = TextUtils.isEmpty(configInfo.keyCode) ? GlobalConfig.KEY_CODE : configInfo.keyCode;
        String key_msg = TextUtils.isEmpty(configInfo.keyMSg) ? GlobalConfig.KEY_MSG : configInfo.keyMSg;

        String data = object.optString(key_data);
        int code = object.optInt(key_code);
        String msg = object.optString(key_msg);

        if (configInfo.isEncrypt()) {
            try {
                data = EncryptUtil.decryptContent(data, "AES", configInfo.encryptKey);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }

        if (GlobalConfig.isOpenLog) {
            LogUtils.dTag("*******************----data----*******************=>" + configInfo.url, "{" + "\"code\":" + code +
                    ",\"data\":" + data +
                    ",\"message\":" + "\"" + msg + "\"" + "}");
        }

        int codeSuccess = configInfo.isCustomCodeSuccess ? configInfo.codeSuccess : GlobalConfig.CODE_SUCCESS;
        if (code == codeSuccess) {

            Type genType = null;
            if (configInfo.converter != null) {
                //优先使用converter
                genType = configInfo.converter.getClass().getGenericSuperclass();
            } else {
                genType = configInfo.callBack.getClass().getGenericSuperclass();
            }
            Type t = ((ParameterizedType) genType).getActualTypeArguments()[0];
            if (t instanceof Class && ((Class) t).getName().equals(String.class.getName())) {
                return (T) data;
            } else {
                return (T) new Gson().fromJson(data, t);
            }
        } else {
            throw new CodeErrorException(msg, code, data,configInfo);
        }
    }
}
