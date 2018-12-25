package com.cai.rxhttp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.cai.rxhttplib.HttpUtil
import com.cai.rxhttplib.callback.BaseSubscriber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tvPost).setOnClickListener {

            HttpUtil.postFormat("common/stockinfo/allCoin")
                .setAppendToken(false)
                .setEncrypt(false)
                .asObservable()
                .subscribe(object : BaseSubscriber<String>() {
                    override fun onError(msg: String?, code: Int, data: String?) {

                    }
                })
        }
    }
}
