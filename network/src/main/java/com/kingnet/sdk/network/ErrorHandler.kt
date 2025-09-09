import com.kingnet.sdk.common.utils.LogUtils

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/27 16:33
 * Description:错误统一处理
 */
interface ErrorHandler {
    fun bizError(code: Int, mas: String)
    fun otherError(throwable: Throwable)
}

internal object ErrorDefaultHandler : ErrorHandler {
    override fun bizError(code: Int, msg: String) {
        LogUtils.e("bizError,code = $code,msg = $msg")
    }

    override fun otherError(throwable: Throwable) {
        LogUtils.e("otherError,throwable = ${throwable.message}")
    }
}