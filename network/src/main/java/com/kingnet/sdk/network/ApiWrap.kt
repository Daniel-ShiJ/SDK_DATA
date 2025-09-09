package com.kingnet.sdk.network

import ErrorDefaultHandler
import ErrorHandler
import com.kingnet.sdk.common.SDKConfig
import com.kingnet.sdk.common.utils.LogUtils
import com.kingnet.sdk.network.responses.adapter.NetworkResponseAdapterFactory
import com.kingnet.sdk.network.responses.converter.GsonConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/11 17:59
 * Description:
 */
object ApiWrap {
    private val logInterceptor = HttpLoggingInterceptor {
        LogUtils.e(msg = it)
    }.setLevel(if (SDKConfig.isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC)

    private const val TIME_OUT_SECONDS = 90
    private val client: OkHttpClient
        get() = OkHttpClient.Builder().run {
            if (SDKConfig.isDebug) {
                addNetworkInterceptor(logInterceptor)
            }
            connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            build().apply {
                dispatcher.maxRequests = dispatcher.maxRequests.shr(2)
            }
        }

    private val servicesMap = ConcurrentHashMap<String, IService>()
    private val errorHandlers = mutableListOf<ErrorHandler>(ErrorDefaultHandler)

    /**
     * 添加错误处理
     */
    fun addErrorHandler(errorHandler: ErrorHandler) = errorHandlers.add(errorHandler)


    fun <T : IService> getService(coroutineScope: CoroutineScope, service: Class<T>, baseUrl: String): T {
        return servicesMap.getOrPut(service.name) {
            Retrofit.Builder().run {
                baseUrl(baseUrl)
                client(client)
                addCallAdapterFactory(NetworkResponseAdapterFactory(object : ErrorHandler {
                    override fun bizError(code: Int, mes: String) {
                        //Dispatchers.Main.immediate： 如果在UI线程加载，不会做特殊处理；如果是在子线程，会通过Handler转发到主线程
                        coroutineScope.launch(Dispatchers.Main.immediate) {
                            errorHandlers.forEach {
                                it.bizError(code, mes)
                            }
                        }
                        LogUtils.d(msg = "bizError:Code:$code - mes:$mes")
                    }

                    override fun otherError(throwable: Throwable) {
                        coroutineScope.launch(Dispatchers.Main.immediate) {
                            errorHandlers.forEach {
                                it.otherError(throwable)
                            }
                        }
                        LogUtils.e(msg = throwable.message.toString(), throwable = throwable)
                    }
                }))
                addConverterFactory(GsonConverterFactory.create())
                build().create(service)
            }
        } as T
    }

    fun <T : IService> getSingleService(coroutineScope: CoroutineScope,service: Class<T>, baseUrl: String): T {
        return Retrofit.Builder().run {
            baseUrl(baseUrl)
            client(client)
            addCallAdapterFactory(NetworkResponseAdapterFactory(object : ErrorHandler {
                override fun bizError(code: Int, mes: String) {
                    //Dispatchers.Main.immediate： 如果在UI线程加载，不会做特殊处理；如果是在子线程，会通过Handler转发到主线程
                    coroutineScope.launch(Dispatchers.Main.immediate) {
                        errorHandlers.forEach {
                            it.bizError(code, mes)
                        }
                    }
                    LogUtils.d(msg = "bizError:Code:$code - mes:$mes")
                }

                override fun otherError(throwable: Throwable) {
                    coroutineScope.launch(Dispatchers.Main.immediate) {
                        errorHandlers.forEach {
                            it.otherError(throwable)
                        }
                    }
                    LogUtils.e(msg = throwable.message.toString(), throwable = throwable)
                }
            }))
            addConverterFactory(GsonConverterFactory.create())
            build().create(service)
        }
    }

}