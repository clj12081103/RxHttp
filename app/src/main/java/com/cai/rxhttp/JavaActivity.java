package com.cai.rxhttp;

import android.os.Bundle;
import android.view.View;
import com.cai.rxhttplib.RxHttp;
import com.cai.rxhttplib.callback.BaseSubscriber;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

public class JavaActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tvPost)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        RxHttp.postFormat("common/stockinfo/allCoin")
                                .setEncrypt(false)
                                .setAppendToken(false)
                                .asObservable()
                                .compose(bindToLifecycle())
                                .subscribe(new BaseSubscriber<List<CoinBean>>() {

                                    @Override
                                    public void onSuccess(List<CoinBean> coinBeans) {

                                    }
                                });

                    }
                });
    }
}
