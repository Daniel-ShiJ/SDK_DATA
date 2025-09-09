package com.kingnet.sdk.collectdata

import android.content.Context

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 14:17
 * Description:
 */
object CollectDataConfig {
    /**
     * 服务器地址
     */
    var baseUrl = ""
    const val VERSION = "1.0.0"

    /**
     * 数据是否压缩
     */
    var isCompress = true

    var mContext:Context ?= null
}