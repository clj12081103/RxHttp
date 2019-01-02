package com.cai.rxhttp

import android.os.Bundle
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.cai.rxhttplib.RxHttp
import com.cai.rxhttplib.callback.BaseSubscriber
import com.cai.rxhttplib.config.ConfigInfo
import com.cai.rxhttplib.convert.JsonConverter
import com.cai.rxhttplib.interfaces.INetManager
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxHttp.init(true, object : INetManager {
            override fun isLogin(): Boolean {
                return true
            }

            override fun autoLogin() {

            }

            override fun getToken(): String {
                return ""
            }

            override fun addCommonParamsAndEncrypt(configInfo: ConfigInfo<*>) {

            }

            override fun updateApp() {

            }
        })


        findViewById<TextView>(R.id.tvPost).setOnClickListener {
            //            RxHttp.postFormat("")
//                .setAppendToken(false)
//                .setEncrypt(false)
//                .callBack(object : MyCallBack<List<CoinBean>>() {
//                    override fun onSuccess(t: List<CoinBean>?) {
//                        LogUtils.i(t)
//                    }
//
//                })
            RxHttp.postFormat("")
                .setAppendToken(false)
                .setEncrypt(false)
                .setConverter(JsonConverter<List<CoinBean>>())
                .asObservable()
                .compose<List<CoinBean>>(bindToLifecycle<Long>())
                .subscribe(object : BaseSubscriber<List<CoinBean>>() {
                    override fun onSuccess(t: List<CoinBean>) {
                        LogUtils.i(t)
                    }
                })

        }
    }
}
