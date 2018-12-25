package com.cai.rxhttplib.retrofit;

import com.cai.rxhttplib.config.GlobalConfig;
import com.cai.rxhttplib.utils.SSLSocketFactoryUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    public static volatile RetrofitClient mInstance;
    private Retrofit retrofit;
    private ApiService service;

    private RetrofitClient() {
        initService();
    }

    private void initService() {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client = httpBuilder
                .readTimeout(GlobalConfig.TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(GlobalConfig.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(GlobalConfig.TIME_OUT, TimeUnit.SECONDS) //设置超时
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(GlobalConfig.baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .build();

        service = retrofit.create(ApiService.class);
    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }

    public ApiService getApiService() {
        return service;
    }


}
