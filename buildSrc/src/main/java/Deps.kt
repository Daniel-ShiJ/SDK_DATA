/**
 * Author:Daniel.ShiJ
 * Date:2024/10/17 22:29
 * Description:依赖统一管理
 */
object Version {
    object ClassPathVersion {
        const val kotlinVersion = "1.8.0"
    }

    const val namespace = "com.kingnet.sdk"
    const val compileSdk = 33
    const val applicationId = "com.kingnet.sdk"
    const val minSdk = 24
    const val targetSdk = 33
    val versionCode = 1
    const val versionName = "1.0.0"

    const val coreKtxVersion = "1.8.0"
    const val appCompatVersion = "1.6.1"
    const val materialVersion = "1.7.0"

    const val retrofitVersion = "2.11.0"
    const val okHttp3Version = "4.12.0"
    const val okioVersion = "3.6.0"
    const val gsonVersion = "2.11.0"

}

object Deps {
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtxVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
    const val material = "com.google.android.material:material:${Version.materialVersion}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Version.retrofitVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okHttp3Version}"
    const val okio = "com.squareup.okio:okio-jvm:${Version.okioVersion}"
    const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Version.okHttp3Version}"

    const val gson = "com.google.code.gson:gson:${Version.gsonVersion}"
}