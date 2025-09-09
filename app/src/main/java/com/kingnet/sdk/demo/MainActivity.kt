package com.kingnet.sdk.demo

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import com.kingnet.sdk.sdk_ky.KingNetSDK

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        findViewById<TextView>(R.id.request).setOnClickListener {
            //模拟请求
            val params = HashMap<String,String>()
//            params["msg"] = "未找到待添加客户"//信息
//            params["step"] = "添加客户,新的客户,maxPage \\u003d 5，first \\u003d 添加客户 ，first \\u003d 新的客户 ，first \\u003d 近三天 ，get(1) \\u003d 勿念 ，get(2) \\u003d ＠微信 ，first \\u003d 三天前 ，get(1) \\u003d 小苹果 ，get(2) \\u003d ＠微信 ，first \\u003d 娜乌西卡 ，get(1) \\u003d ＠微信 ，get(2) \\u003d 我是娜乌西卡 ，first \\u003d Juliana ，get(1) \\u003d ＠微信 ，get(2) \\u003d 我是Juliana ，first \\u003d 宇翔123456 ，get(1) \\u003d ＠微信 ，get(2) \\u003d 我是宇翔 ，first \\u003d 勿念 ，first \\u003d ＠微信 ，first \\u003d 小苹果 ，first \\u003d ＠微信 ，first \\u003d 娜乌西卡 ，first \\u003d ＠微信 ，first \\u003d Juliana ，first \\u003d ＠微信 ，first \\u003d 宇翔123456 ，first \\u003d ＠微信 ，"//操作步骤

            params["code"] = "12"
            params["idDev"] = "12"
            params["nickName"] = "12"
            params["gamename"] = "12"
            params["versionApp"] = "12"
            params["manufacturer"] = "${Build.MANUFACTURER}"
            params["msg"] = "[{\\\"cause\\\":{\\\"detailMessage\\\":\\\"Unable to resolve host \\\\\\\"dev-nebula-command.kingnet.com\\\\\\\": No address associated with hostname\\\",\\\"stackTrace\\\":[],\\\"suppressedExceptions\\\":[]},\\\"detailMessage\\\":\\\"xhr poll error\\\",\\\"stackTrace\\\":[],\\\"suppressedExceptions\\\":[]}]"//信息
            params["step"] = ""//操作步骤
            KingNetSDK.popEvent("123","testEvent",params)

        }

        findViewById<TextView>(R.id.mockEx).setOnClickListener {
            //模拟异常
        }
    }
}