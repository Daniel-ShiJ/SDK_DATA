package com.kingnet.sdk.sdk_ky

import android.app.Application
import android.content.Context
import com.kingnet.sdk.common.utils.LogUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 15:42
 * Description:
 */
abstract class SDKApplication : Application() {
    companion object {
        lateinit var mContext: Context
        lateinit var ioApplicationScope: CoroutineScope
        lateinit var applicationScope: CoroutineScope
    }

    init {
        ioApplicationScope =
            CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
                LogUtils.e(
                    "ioApplicationScope:\n${throwable.message.toString()}", throwable = throwable
                )
            })

        applicationScope =
            CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
                LogUtils.e(
                    "applicationScope:\n${throwable.message.toString()}", throwable = throwable
                )
            })
    }
}