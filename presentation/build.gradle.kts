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
        multiDexEnabled = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            multiDexEnabled = true
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.ktxCore)
    implementation(Libraries.fragmentKtx)

    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.coroutinesPlayServices)

    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAnalytics)
    implementation(Libraries.firebaseCrashlytics)
    implementation(Libraries.firebasePerformance)
    implementation(Libraries.firebaseDatabase)
    implementation(Libraries.firebaseAuth)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation(Libraries.materialDesign)
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    implementation(Libraries.converterGson)
    implementation(Libraries.okHttp)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.android.support:recyclerview-v7:28.0.0")

    koinAndroid()
    glide()

    androidxDependencies()
}


fun DependencyHandlerScope.androidxDependencies() {
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
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