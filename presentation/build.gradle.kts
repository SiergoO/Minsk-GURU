import Libraries.androidXCamera
import Libraries.glide
import Libraries.koinAndroid

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.googleServicesPlugin)
    id(BuildPlugins.firebaseCrashlytics)
    id(BuildPlugins.firebasePerformance)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
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
        applicationId = "com.minsk.guru"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.ktxCore)
    implementation(Libraries.fragmentKtx)
    implementation("com.android.support:support-compat:28.0.0")

    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)

    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAnalytics)
    implementation(Libraries.firebaseCrashlytics)
    implementation(Libraries.firebasePerformance)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    implementation(Libraries.converterGson)
    implementation(Libraries.okHttp)

    koinAndroid()
    glide()

    androidxDependencies()

    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
}


fun DependencyHandlerScope.androidxDependencies() {
    androidXCamera()
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation(Libraries.androidxPaging)
    implementation(Libraries.androidxViewModel)
    implementation(Libraries.androidxLiveData)
    implementation(Libraries.androidxLifecycleExtensions)

    implementation(Libraries.androidxNavigationFragment)
    implementation(Libraries.androidxNavigationUi)
    implementation(Libraries.androidxNavigationFeatures)
    implementation(Libraries.androidxNavigationCompose)

    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)

    implementation(Libraries.recyclerView)
    implementation(Libraries.recyclerViewSelection)
}