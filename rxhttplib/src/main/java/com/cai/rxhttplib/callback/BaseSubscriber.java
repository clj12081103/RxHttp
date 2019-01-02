package com.cai.rxhttplib.callback;

import com.cai.rxhttplib.config.ConfigInfo;
import com.cai.rxhttplib.interfaces.IProgressCallback;
import com.cai.rxhttplib.utils.ExceptionDispatcher;
import io.reactivex.observers.DisposableObserver;

public abstract class BaseSubscriber<T> extends DisposableObserver<T> implements IProgressCallback {

    private boolean showLoadingDialog;

    public BaseSubscriber() {
    }

    public BaseSubscriber(boolean showLoadingDialog) {
        this.showLoadingDialog = showLoadingDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (showLoadingDialog) {

        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        ExceptionDispatcher.dispatchException(e, this);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public void onError(String msg, int code, String data) {

    }

    public void onError(String msg, int code, String data, ConfigInfo configInfo) {
        if (configInfo.showErrorMsg) {
            onError(msg, code, data);
        }
    }

    /**
     * 都是B作为单位
     */
    @Override
    public void onProgressChange(long transPortedBytes, long totalBytes, ConfigInfo info) {

    }

    /**
     * @param transPortedBytes
     * @param totalBytes
     * @param fileIndex
     * @param filesCount       总的上传文件数量
     */
    @Override
    public void onFilesUploadProgress(long transPortedBytes, long totalBytes, int fileIndex, int filesCount, ConfigInfo info) {
        onProgressChange(transPortedBytes, totalBytes, info);
    }
}
