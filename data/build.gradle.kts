import Libraries.koinAndroid
import Libraries.room

repositories {
    mavenCentral()
    google()
}

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.serialization)
    id(BuildPlugins.googleServicesPlugin)
}

android {
    compileSdk = AndroidSdk.compile
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
        }
    }
}

dependencies {
    implementation(project(path = ":domain"))
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutines)
    implementation(Libraries.kotlinxSerialization)
    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAnalytics)
    implementation(Libraries.firebaseCrashlytics)
    implementation(Libraries.firebasePerformance)
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    implementation(Libraries.converterGson)
    implementation(Libraries.okHttp)
    implementation(Libraries.firebaseDatabase)
    implementation(Libraries.firebaseAuth)
    implementation(Libraries.googlePlaces)
    koinAndroid()
    room()
}