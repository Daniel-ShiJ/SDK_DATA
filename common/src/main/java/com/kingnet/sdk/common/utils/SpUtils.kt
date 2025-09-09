package com.kingnet.sdk.common.utils

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 10:10
 * Description:
 */
object SpUtils {
    @Throws(IOException::class)
    fun slurp(inputStream: InputStream): ByteArray {
        val buffer = ByteArrayOutputStream()
        var nRead: Int
        val data = ByteArray(8192)
        while (inputStream.read(data, 0, data.size).also { nRead = it } != -1) {
            buffer.write(data, 0, nRead)
        }
        buffer.flush()
        return buffer.toByteArray()
    }

    private val SDK_SP_NAME = "sdk_sp_name"
    fun <T> save(context: Context?,key: String, t: T) {
        context?.apply {
            when (t) {
                is String -> {
                    getSharedPreferences(SDK_SP_NAME, Context.MODE_PRIVATE).edit().putString(key, t)
                        .apply()
                }
            }
        }
    }

    fun getString(context: Context?,key: String,default:String = ""): String {
        return context?.let {
            it.getSharedPreferences(SDK_SP_NAME, Context.MODE_PRIVATE).getString(key,default)
        }?:""
    }

    fun getBoolean(context: Context?,key: String,default:Boolean = false): Boolean {
        return context?.let {
            it.getSharedPreferences(SDK_SP_NAME, Context.MODE_PRIVATE).getBoolean(key, default)
        } ?: false
    }
}