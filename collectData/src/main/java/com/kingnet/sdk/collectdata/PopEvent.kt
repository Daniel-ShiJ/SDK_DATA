package com.kingnet.sdk.collectdata

import android.os.Build
import com.kingnet.sdk.collectdata.bean.CollectDataBean
import com.kingnet.sdk.collectdata.utils.DataMetaUtils
import com.kingnet.sdk.collectdata.utils.EncryptUtils
import com.kingnet.sdk.collectdata.utils.xxtea.XXTEA
import com.kingnet.sdk.common.utils.LogUtils
import com.kingnet.sdk.common.utils.PhoneUtils
import com.kingnet.sdk.network.ApiWrap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 10:40
 * Description:
 */
object PopEvent {
    /**
     * 公共参数
     */
    private val commonParams = JSONObject().apply {
        CollectDataConfig.mContext?.apply {
            //token
            putOpt("app_key", DataMetaUtils.getToken())
            //imei
            putOpt("sdk_did",PhoneUtils.getDeviceId(this))
            //获取客户端版本号
            putOpt("gameversion",PhoneUtils.getVersionName(this))
            //mac地址
            putOpt("mac", PhoneUtils.getMacAddress(this))
//            //androidId
            putOpt("ssaid",PhoneUtils.getDeviceId(this))
//            //获取包名
            putOpt("bundle_id",PhoneUtils.getPackageName(this))
//            //获取应用名
            putOpt("gamename",PhoneUtils.getAppName(this))
            //终端
            putOpt("terminal","Android")
            //手机语言
            putOpt("lang",PhoneUtils.getLanguage(this))
            //手机型号
            putOpt("_model", Build.MODEL)
            //手机品牌
            putOpt("_brand",Build.BRAND)
            //手机网络类型
            putOpt("_nettype",PhoneUtils.getNetWorkType(this))
//            //分辨率
            putOpt("_res",PhoneUtils.getRes(this))
//            //系统版本号
            putOpt("_osver","${Build.VERSION.SDK_INT}")
            //采集sdk类型
            putOpt("_sdk","Android")
            //数据sdk版本
            putOpt("_sdkserver",CollectDataConfig.VERSION)
        }
    }

    /**
     * 互斥锁是一种同步原语，用于保护关键代码段或共享资源，防止多个线程的并发访问。互斥锁确保一次只有一个协程可以获取锁，从而允许对受保护资源的独占访问。
     * 互斥锁在需要对 共享资源 进行独占访问 或 在并发环境中修改可变状态时 特别有用。它通过串行化对受保护资源的访问，有助于防止竞争条件并确保线程安全。
     */
    private val mutex = Mutex()
    /**
     * 上传事件
     * @param eventName 事件名
     * @param params 参数
     */
    suspend fun popEvent(
        userId: String,
        eventName: String, params: Map<String, String>, coroutineScope: CoroutineScope
    ) {
        mutex.withLock {// 用于获取锁。确保一次只有一个协程可以在任何给定时间执行临界区的代码。尝试获取锁的其他协程将被挂起，直到锁变为可用为止。
            /*****************************临界区*****************************/
            // 在这里访问共享资源
            val encryptStr =
                compressPostData(buildCollectDataBean(userId, eventName, params).selfToJson())//最终传输的内容
            ApiWrap.getService(coroutineScope, IPopService::class.java, CollectDataConfig.baseUrl)
                .upload(
                    encryptStr.toRequestBody("text/plain".toMediaTypeOrNull()),//post raw发出
                    EncryptUtils.getAuthorizationString(encryptStr)
                )
            /*****************************临界区*****************************/
        }
    }

    /**
     * 参数何必并转化为string
     */
    private fun buildCollectDataBean(userId:String,eventName:String,params:Map<String,String>):CollectDataBean{
        val properJson = commonParams
        params.forEach { (k, v) ->
            properJson.putOpt(k,v)
        }
        return CollectDataBean(
            userId,
            DataMetaUtils.getProject(),
            PhoneUtils.getDeviceId(CollectDataConfig.mContext!!),
            eventName,
            "${System.currentTimeMillis()}",
            properJson
        )
    }

    /**
     * 加密
     */
    private fun compressPostData(source:String,isCompress: Boolean = CollectDataConfig.isCompress):String {
        var result = ""
        try {
            if (isCompress) {
                result = EncryptUtils.encodeData(source)
            } else {
                result = XXTEA.encryptToBase64String(source, DataMetaUtils.getToken())
            }
        } catch (e: Exception) {
            LogUtils.e("${e.message}")
        }
        return result
    }

}