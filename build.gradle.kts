// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by extra("1.4.21")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
        classpath(BuildPlugins.kotlinSerializationPlugin)
        classpath(BuildPlugins.googleServices)
        classpath(BuildPlugins.firebaseCrashlyticsClasspath)
        classpath(BuildPlugins.firebasePerformanceClasspath)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.3.8")
        classpath("com.android.tools.build:gradle")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}