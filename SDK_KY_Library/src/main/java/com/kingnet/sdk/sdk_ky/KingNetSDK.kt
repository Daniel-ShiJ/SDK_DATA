package com.kingnet.sdk.sdk_ky

import android.app.Application
import com.kingnet.sdk.collectdata.CollectData
import com.kingnet.sdk.collectdata.PopEvent
import com.kingnet.sdk.common.SDKConfig

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 09:52
 * Description:初始化sdk
 */
object KingNetSDK {
    /**
     * 初始化
     */
    private fun init(application: Application) {
        SDKApplication.mContext = application
    }

    fun setIsDebug(isDebug:Boolean){
        SDKConfig.isDebug = isDebug
    }

    /**
     * 初始化数据收集
     */
    fun initCollectData(application: Application, url: String) {
        init(application)
        CollectData.init(application, url)
    }

    /**
     * 上传事件
     */
    fun popEvent(userId: String, eventName: String, params: Map<String, String> = emptyMap()) {
        HttpRequest.request {
            PopEvent.popEvent(userId, eventName, params, this)
        }
    }
}