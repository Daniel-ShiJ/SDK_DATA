package com.kingnet.sdk.network.responses.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/28 10:25
 * Description:
 */
class GsonConverterFactory(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create() = create(Gson())

        private fun create(gson: Gson?): GsonConverterFactory {
            gson?.let {
                return GsonConverterFactory(gson)
            } ?: throw NullPointerException("gson == null")
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        check(type is ParameterizedType) {
            "type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }
        return GsonResponseBodyConverter(
            gson,
            gson.getAdapter(TypeToken.get(getParameterUpperBound(0, type)))
        )
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>?= GsonRequestBodyConverter(gson,gson.getAdapter(TypeToken.get(type)))
}