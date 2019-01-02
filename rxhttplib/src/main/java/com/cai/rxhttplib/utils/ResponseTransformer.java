package com.cai.rxhttplib.utils;


import com.cai.rxhttplib.config.ConfigInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 对返回的数据进行处理，区分异常的情况。
 */

public class ResponseTransformer {

    public static <T> ObservableTransformer<ResponseBody, T> handleResult(final ConfigInfo<T> configInfo) {
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .onErrorResumeNext(new ErrorResumeFunction<T>(configInfo, false))
                        .flatMap(new ResponseFunction<T>(configInfo, false));
            }
        };
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误,HttpException,socket超时等等。
     * 无法捕获rxjava内部产生的异常,比如设置的超时异常:
     * ObservableTimeoutTimed$TimeoutObserver.onTimeout(ObservableTimeoutTimed.java:132)
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends ResponseBody>> {

        ConfigInfo<T> info;
        boolean fromCache;

        public ErrorResumeFunction(ConfigInfo<T> configInfo, boolean fromCache) {
            this.info = configInfo;
            this.fromCache = fromCache;
        }

        @Override
        public ObservableSource<? extends ResponseBody> apply(Throwable throwable) throws Exception {
            return Observable.error(throwable);
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<ResponseBody, ObservableSource<T>> {

        ConfigInfo<T> info;
        boolean fromCache;

        public ResponseFunction(ConfigInfo<T> info, boolean fromCache) {
            this.info = info;
            this.fromCache = fromCache;
        }

        @Override
        public ObservableSource<T> apply(ResponseBody responseBody) throws Exception {
            String responseStr = responseBody.string();
            return Observable.just(ResponseParser.parseResponse(responseStr, info))
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }


}
