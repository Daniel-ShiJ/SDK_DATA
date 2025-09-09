package com.kingnet.sdk.common.extensions

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 13:56
 * Description:
 */
fun ByteArray.hex() = joinToString("") { "%02x".format(it) }