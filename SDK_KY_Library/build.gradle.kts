plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
//    id("com.kezong.fat-aar")
}

android {
    namespace = "com.kingnet.sdk.sdk_ky"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
        version = Version.versionName
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(project(mapOf("path" to ":collectData")))


//
//    /************ 导包时 打开，把所有涉及到的 module和依赖 都合并进去 *************/
//    embed(project(mapOf("path" to ":collectData")))
//    embed(project(mapOf("path" to ":common")))
//    embed(project(mapOf("path" to ":network")))
//    embed(project(mapOf("path" to ":config")))
//
//    embed(Deps.retrofit)
//    embed(Deps.retrofitGsonConverter)
//    embed(Deps.okhttp)
//    embed(Deps.okio)//只有合并时使用
//    embed(Deps.okhttpLoggingInterceptor)
//    embed(Deps.gson)
}