object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "4.1.2"
        const val googleServices = "4.3.4"
        const val firebaseCrashlyticsClasspath = "2.3.0"
        const val firebasePerformanceClasspath = "1.3.2"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val kotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val firebaseCrashlyticsClasspath =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsClasspath}"
    const val firebasePerformanceClasspath =
        "com.google.firebase:perf-plugin:${Versions.firebasePerformanceClasspath}"
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val googleServicesPlugin = "com.google.gms.google-services"
    const val kotlin = "kotlin"
    const val serialization = "kotlinx-serialization"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinParcelize = "kotlin-parcelize"

    const val firebasePerformance = "com.google.firebase.firebase-perf"
    const val firebaseCrashlytics = "com.google.firebase.crashlytics"
}