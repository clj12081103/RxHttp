package com.cai.rxhttplib.utils;

import android.support.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;


public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    Scheduler newThread();

    @NonNull
    <T> ObservableTransformer<T, T> toUI();
}
