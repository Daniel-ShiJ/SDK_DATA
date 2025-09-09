package com.kingnet.sdk.common.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/11 18:07
 * Description:
 */
object MetaUtils {
    fun getMetaValue(context: Context,metaKey: String): String {
        return realGetMetaValue(context,metaKey)
    }

    private fun realGetMetaValue(context: Context, metaKey: String): String {
        var result = ""
        try {
            context.apply {
                result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.GET_META_DATA
//                        PackageManager.ApplicationInfoFlags.of(0)
                    ).metaData.getString(metaKey).toString()
                } else {
                    packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.GET_META_DATA
                    ).metaData.getString(metaKey).toString()
                }
            }
        } catch (e: Exception) {
            LogUtils.e(tag = "MetaUtils realGetMetaValue", throwable = e)
        }
        LogUtils.e("metaKey = $metaKey,result = $result")
        return result
    }

    fun setMetaValue(context: Context, metaKey: String, value: String) {
        try {
            context.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.ApplicationInfoFlags.of(0)
                    ).metaData.putString(metaKey, value)
                } else {
                    packageManager.getApplicationInfo(
                        packageName,
                        PackageManager.GET_META_DATA
                    ).metaData.putString(metaKey, value)
                }
            }
        } catch (e: Exception) {
            LogUtils.e(tag = "MetaUtils setMetaValue", throwable = e)
        }
    }
}