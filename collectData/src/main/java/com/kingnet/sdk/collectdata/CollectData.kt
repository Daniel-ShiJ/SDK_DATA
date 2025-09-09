package com.kingnet.sdk.collectdata

import android.app.Application
import com.kingnet.sdk.collectdata.utils.DataMetaUtils

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 10:07
 * Description:
 */
object CollectData{
    /**
     * 初始化
     */
    fun init(application: Application,url:String) {
        application.apply {
            CollectDataConfig.mContext = this
            CollectDataConfig.baseUrl = url
            DataMetaUtils.saveAppId()
            DataMetaUtils.saveToken()
            DataMetaUtils.saveTopic()
            DataMetaUtils.saveProject()
        }
    }

    fun getRequest() = PopEvent
}