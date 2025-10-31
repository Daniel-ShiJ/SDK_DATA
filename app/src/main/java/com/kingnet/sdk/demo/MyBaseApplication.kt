package com.kingnet.sdk.demo

import com.kingnet.sdk.sdk_ky.KingNetSDK
import com.kingnet.sdk.sdk_ky.SDKApplication


/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 10:12
 * Description:
 */
class MyBaseApplication:SDKApplication() {
    override fun onCreate() {
        super.onCreate()
        KingNetSDK.setIsDebug(BuildConfig.DEBUG)
        KingNetSDK.initCollectData(this,"https://kdclog.kingnetdc.com")
    }
}