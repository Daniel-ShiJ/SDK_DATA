package com.kingnet.sdk.collectdata

import com.kingnet.sdk.collectdata.utils.DataMetaUtils
import com.kingnet.sdk.network.IService
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 11:29
 * Description:
 */
interface IPopService : IService {
    @Headers("Version:2")
    @POST("/dana")
    suspend fun upload(
        @Body source: RequestBody,
        @Header("Authorization") author: String,
        @Header("Content-Type") str: String = if (CollectDataConfig.isCompress) "encryption/x-gzip" else "encryption/json",
        @Header("Topic") topic: String = DataMetaUtils.getTopic()
    ): ResponseBody
}