package com.kingnet.sdk.network.responses.adapter

import ErrorHandler
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/27 16:26
 * Description:重写CallAdapter
 */
class NetworkResponseAdapter(private val type: Type, private val errorHandler: ErrorHandler) :
    CallAdapter<Any, Call<Any>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<Any>): Call<Any> =
        NetworkResponseCall(call, type as ParameterizedType, errorHandler)
}