package com.kingnet.sdk.collectdata.utils

import com.kingnet.sdk.collectdata.utils.DataMetaUtils.getToken
import com.kingnet.sdk.collectdata.utils.xxtea.XXTEA
import com.kingnet.sdk.common.extensions.md5
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 14:15
 * Description:
 */
object EncryptUtils {
    /**
     * 头文件授权
     */
    fun getAuthorizationString(source: String): String {
        return with(DataMetaUtils) {
            //http 头的 Version 字段，现在只支持 2，其他的不支持
            (getToken() + getTopic() + 2 + getToken() + source).md5()//日志收集简介：https://kingnet.feishu.cn/wiki/DonAw3Y3FifgZJkKYIXcCZQyneb?from=from_copylink
        }
    }

    /**
     * 压缩加密
     */
    fun encodeData(source:String):String{
        var result = ""
        ByteArrayOutputStream(source.toByteArray().size).use {
            GZIPOutputStream(it).use {gZIP->
                gZIP.write(source.toByteArray())
            }
            val compressed = it.toByteArray()
            result = XXTEA.encryptToBase64String(String(Base64Coder.encode(compressed)),getToken())
        }
        return result
    }

}