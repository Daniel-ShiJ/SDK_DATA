plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kingnet.sdk.network"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
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
    api(project(":common"))
    api(Deps.retrofit)
    api(Deps.retrofitGsonConverter)
    api(Deps.okhttp)
    api(Deps.okhttpLoggingInterceptor)
}