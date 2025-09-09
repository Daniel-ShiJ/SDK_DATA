package com.kingnet.sdk.sdk_ky

import androidx.lifecycle.MutableLiveData
import com.kingnet.sdk.common.utils.LogUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 15:50
 * Description:
 */
internal object HttpRequest {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        LogUtils.e(throwable = throwable)
    }
    private val requestException = MutableLiveData<Exception>()

    /**
     * 网络请求
     */
    fun request(
        catch: suspend CoroutineScope.(e: Exception) -> Unit = {},
        finally: suspend CoroutineScope.() -> Unit = {},
        lambda: suspend CoroutineScope.() -> Unit
    ) {
        SDKApplication.ioApplicationScope.launch(exceptionHandler) {
            try {
                lambda()
            } catch (e: Exception) {
                LogUtils.e("${e.message}")
                requestException.postValue(e)
                catch(e)
            } finally {
                finally()
            }
        }
    }
}