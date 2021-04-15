import org.gradle.api.artifacts.dsl.DependencyHandler

const val kotlinVersion = "1.4.21"

object Libraries {
    private object Versions {
        const val navigation = "2.3.3"
        const val navigationCompose = "1.0.0-alpha06"
        const val jetpack = "1.2.0"
        const val material = "1.1.0"
        const val constraintLayout = "2.0.4"
        const val ktx = "1.3.1"
        const val ktxFragment = "1.2.5"
        const val coroutines = "1.4.2"
        const val serialization = "1.0.1"
        const val lifecycle = "2.2.0"
        const val koin = "2.2.2"
        const val recyclerview = "1.1.0"
        const val recyclerViewSelection = "1.0.0"
        const val room = "2.2.6"
        const val glide = "4.11.0"
        const val cameraX = "1.0.0-beta08"
        const val paging = "2.1.2"
        const val firebaseDatabase = "19.2.1"
        const val firebaseBom = "26.3.0"
        const val firebaseAuth = "20.0.3"
        const val retrofit = "2.6.0"
        const val gson = "2.8.5"
        const val converterGson = "2.5.0"
        const val okHttp = "3.12.0"
    }

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    const val firebaseDatabase = "com.google.firebase:firebase-database:${Versions.firebaseDatabase}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebasePerformance = "com.google.firebase:firebase-perf-ktx"

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:" +
            Versions.constraintLayout
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.ktxFragment}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" +
            Versions.coroutines
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:" +
            Versions.serialization

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    private const val fuelPrefix = "com.github.kittinunf.fuel"

    private const val androidXCameraPrefix = "androidx.camera"
    const val androidxPaging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"

    // Koin for Kotlin
    const val koinKotlin = "org.koin:koin-core:${Versions.koin}"

    // Koin for Tests
    private const val koinTest = "org.koin:koin-test:${Versions.koin}"

    // Koin for Android
    private const val koinAndroid = "org.koin:koin-android:${Versions.koin}"

    // or Koin for Lifecycle scoping
    private const val koinAndroidx = "org.koin:koin-androidx-scope:${Versions.koin}"

    // or Koin for Android Architecture ViewModel
    private const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    // or Koin for Android Fragment Factory (unstable version)
    private const val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"

    const val androidxLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val androidxViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val androidxLifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val androidxNavigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val androidxNavigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val androidxNavigationFeatures =  "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    const val androidxNavigationCompose = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"

    const val materialDesign = "com.google.android.material:material:${Versions.material}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:" +
            Versions.recyclerViewSelection

    fun DependencyHandler.koinAndroid() {
        implementation(koinAndroid)
        implementation(koinAndroidx)
        implementation(koinViewModel)
        implementation(koinFragment)
        testImplementation(koinTest)
    }

    fun DependencyHandler.androidXCamera(version: String = Versions.cameraX) {
        arrayOf(
            "camera-core",
            "camera-camera2",
            "camera-lifecycle"
        ).forEach {
            implementation("$androidXCameraPrefix:$it:$version")
        }
        implementation("$androidXCameraPrefix:camera-view:1.0.0-alpha20")
        implementation("$androidXCameraPrefix:camera-extensions:1.0.0-alpha20")
    }

    fun DependencyHandler.room(version: String = Versions.room) {
        implementation("androidx.room:room-runtime:$version")
        implementation("androidx.room:room-ktx:$version")
        kapt("androidx.room:room-compiler:$version")
    }

    fun DependencyHandler.glide(version: String = Versions.glide) {
        implementation("com.github.bumptech.glide:glide:$version")
        kapt("com.github.bumptech.glide:compiler:$version")
    }
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.12"
        const val testRunner = "1.1.0-alpha4"
        const val espresso = "3.1.0-alpha4"
        const val mockito = "1.10.19"
        const val androidxTest = "1.0.0"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val androidxTest = "androidx.test:core:${Versions.androidxTest}"
}