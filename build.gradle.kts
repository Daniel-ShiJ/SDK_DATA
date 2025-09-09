plugins{
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version Version.ClassPathVersion.kotlinVersion apply false
}

buildscript{
    repositories{
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath(kotlin("gradle-plugin", Version.ClassPathVersion.kotlinVersion))
        classpath(kotlin("serialization", Version.ClassPathVersion.kotlinVersion))
        classpath("com.github.kezong:fat-aar:1.3.8")
    }
}


task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}