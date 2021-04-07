import Libraries.koinAndroid
import Libraries.room

repositories {
    jcenter()
}

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.serialization)
    id(BuildPlugins.googleServicesPlugin)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
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
            buildConfigField("String", "DATABASE_NAME", "\"MinskGuruDatabase\"")
        }
        getByName("debug") {
            buildConfigField("String", "DATABASE_NAME", "\"MinskGuruDebugDatabase\"")
        }
        getByName("release"){
            buildConfigField("String", "BASE_URL", "\"https://search-maps.yandex.ru\"")
        }
        getByName("debug"){
            buildConfigField("String", "BASE_URL", "\"https://search-maps.yandex.ru\"")
        }
    }
    buildFeatures {
        dataBinding = true
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
    koinAndroid()
    room()
    implementation("io.ktor:ktor-client-websockets:1.5.1")
    implementation("io.ktor:ktor-client-core:1.5.1")
    implementation("io.ktor:ktor-client-okhttp:1.5.1")
    implementation(Libraries.androidxPaging)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
    testImplementation(TestLibraries.mockito)
    testImplementation(TestLibraries.androidxTest)
}