package com.cai.rxhttplib.exceptions;

import com.cai.rxhttplib.config.ConfigInfo;

public class CodeErrorException extends Exception {
    public String msg;
    public int code;
    public String data;
    public ConfigInfo configInfo;

    public CodeErrorException(String msg, int code, String data,ConfigInfo configInfo) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.configInfo = configInfo;
    }
}
