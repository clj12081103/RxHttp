package com.cai.rxhttplib.callback;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void onError(String msg,int code,String data);
}
