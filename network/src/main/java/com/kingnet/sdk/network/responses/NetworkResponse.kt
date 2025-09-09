package com.kingnet.sdk.network.responses

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/24 18:24
 * Description:网络请求 响应类
 */
sealed class NetworkResponse<out T : Any> {
    /**
     * 请求成功
     */
    data class Success<T : Any>(val data: T) : NetworkResponse<T>()

    /**
     * 业务错误
     */
    data class BizError(val errorCode: Int = 0, val errorMessage: String = "") :
        NetworkResponse<Nothing>()

    /**
     * 其他错误
     */
    data class OtherError(val throwable: Throwable) : NetworkResponse<Nothing>()
}

inline val NetworkResponse<*>.isSuccess: Boolean
    get() {
        return this is NetworkResponse.Success
    }

fun <T : Any> NetworkResponse<T>.getOrNull(): T? = when (this) {
    is NetworkResponse.Success -> data
    is NetworkResponse.BizError -> null
    is NetworkResponse.OtherError -> null
}

fun <T : Any> NetworkResponse<T>.whenSuccess(block: (T) -> Unit) =
    (this as? NetworkResponse.Success)?.data?.also(block)
