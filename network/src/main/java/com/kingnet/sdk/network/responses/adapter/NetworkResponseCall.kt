package com.kingnet.sdk.network.responses.adapter

import ErrorHandler
import com.kingnet.sdk.network.responses.NetworkResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.ParameterizedType

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/27 16:35
 * Description:自定义Response的Call
 */
class NetworkResponseCall(
    private val delegate: Call<Any>,
    private val wrapperType: ParameterizedType,
    private val errorHandler: ErrorHandler
) : Call<Any> {
    override fun clone(): Call<Any> = NetworkResponseCall(delegate, wrapperType, errorHandler)
    override fun execute(): Response<Any> =
        throw UnsupportedOperationException("${javaClass.name} doesn't support execute")

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object:Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body is NetworkResponse.BizError){
                        errorHandler?.bizError(body.errorCode,body.errorMessage)
                    }
                    callback.onResponse(this@NetworkResponseCall,Response.success(body))
                }else{
                    val exception = HttpException(response)
                    errorHandler?.otherError(exception)
                    callback.onResponse(this@NetworkResponseCall,Response.success(NetworkResponse.OtherError(exception)))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                if(call.isCanceled) return
                errorHandler?.otherError(t)
                callback.onResponse(this@NetworkResponseCall,Response.success(NetworkResponse.OtherError(t)))
            }

        })
    }
}