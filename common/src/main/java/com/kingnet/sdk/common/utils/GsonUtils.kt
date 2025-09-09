package com.kingnet.sdk.common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.concurrent.ConcurrentHashMap

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 13:58
 * Description:
 */
object GsonUtils {
    private const val DEFAULT_KEY = "default_gson_key"
    private val gson = ConcurrentHashMap<String, Gson>()

    private fun getGson(): Gson {
        return gson.getOrPut(DEFAULT_KEY){ Gson() }
    }

    fun <T> fromJson(json:String,clazz: Class<T>) = getGson().fromJson(json,clazz)

    fun <T> fromJson(json:String,typeOfT: TypeToken<T>) = getGson().fromJson(json,typeOfT)

    fun toJson(any: Any) = getGson().toJson(any)
}