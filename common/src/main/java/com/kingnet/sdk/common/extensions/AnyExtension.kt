package com.kingnet.sdk.common.extensions

import com.kingnet.sdk.common.utils.GsonUtils
import org.json.JSONObject

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 13:57
 * Description:
 */
fun Any.toJson(): String = GsonUtils.toJson(this)

fun Any.toJsonObject() = JSONObject(this.toJson())