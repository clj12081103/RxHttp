package com.cai.rxhttplib.interfaces;


import com.cai.rxhttplib.config.ConfigInfo;

/**
 * Created by hss on 2018/7/29.
 */

public interface IProgressCallback {

    void onFilesUploadProgress(long transPortedBytes, long totalBytes, int fileIndex, int filesCount, ConfigInfo info);

    void onProgressChange(long transPortedBytes, long totalBytes, ConfigInfo info);
}
