package com.kingnet.sdk.common.utils

import android.util.Log
import com.kingnet.sdk.common.SDKConfig

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/11 16:48
 * Description:
 */
object LogUtils {
    private const val TAG = "《--- SDK ---》"
    private val isDebug
        get() = SDKConfig.isDebug

    fun d(msg:String,tag:String = TAG, ) = if (isDebug) Log.d(tag,msg) else {}

    fun e(msg:String,tag:String = TAG, ) = if (isDebug) Log.e(tag,msg) else {}

    fun e(msg:String="", tag:String = TAG,throwable: Throwable) = if (isDebug) Log.e(tag,msg,throwable) else {}
}