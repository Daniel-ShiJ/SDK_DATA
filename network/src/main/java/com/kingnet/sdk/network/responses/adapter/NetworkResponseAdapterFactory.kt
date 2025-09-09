package com.kingnet.sdk.network.responses.adapter

import ErrorHandler
import com.kingnet.sdk.network.responses.NetworkResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/27 16:24
 * Description:自定义AdapterFactory
 */
class NetworkResponseAdapterFactory(private val errorHandler: ErrorHandler) :
    CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) return null
        check(returnType is ParameterizedType) { "return type must be parameterized as Call<NetworkResponse<<xx>> or Call<NetworkResponse<out xx>>" }

        val responseType = getParameterUpperBound(0, returnType)//get parameterized type

        if (getRawType(responseType) != NetworkResponse::class.java) return null

        return NetworkResponseAdapter(responseType, errorHandler)
    }
}