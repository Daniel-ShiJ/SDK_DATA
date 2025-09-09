plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kingnet.sdk.common"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
    }

    buildTypes {
        release {
        }
        debug {  }
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
    api(project(":config"))
}