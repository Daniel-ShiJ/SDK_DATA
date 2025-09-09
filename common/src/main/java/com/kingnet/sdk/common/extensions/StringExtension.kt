package com.kingnet.sdk.common.extensions

import java.security.MessageDigest

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 13:55
 * Description:
 */
fun String.md5() = MessageDigest.getInstance("MD5").digest(this.toByteArray()).hex()