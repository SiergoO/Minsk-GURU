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
            buildConfigField("String", "FIREBASE_DATABASE_BASE_URL", "\"https://minsk-guru-default-rtdb.firebaseio.com/\"")
        }
        getByName("debug") {
            buildConfigField("String", "FIREBASE_DATABASE_BASE_URL", "\"https://minsk-guru-default-rtdb.firebaseio.com/\"")
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
    implementation(Libraries.firebaseUiDatabase)
    implementation(Libraries.firebaseAuth)
    implementation(Libraries.paging)
    koinAndroid()
    room()
}