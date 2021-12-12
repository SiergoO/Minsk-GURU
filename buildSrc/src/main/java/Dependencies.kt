import org.gradle.api.artifacts.dsl.DependencyHandler

const val kotlinVersion = "1.5.31"

object Libraries {
    private object Versions {
        const val navigation = "2.3.3"
        const val navigationCompose = "1.0.0-alpha06"
        const val jetpack = "1.2.0"
        const val material = "1.4.0-beta01"
        const val constraintLayout = "2.0.4"
        const val ktx = "1.3.1"
        const val ktxFragment = "1.2.5"
        const val coroutines = "1.4.2"
        const val serialization = "1.0.1"
        const val lifecycle = "2.2.0"
        const val koin = "2.2.3"
        const val recyclerview = "1.1.0"
        const val recyclerViewSelection = "1.0.0"
        const val room = "2.3.0"
        const val glide = "4.12.0"
        const val firebaseDatabase = "19.2.1"
        const val firebaseBom = "26.3.0"
        const val firebaseAuth = "20.0.3"
        const val retrofit = "2.6.0"
        const val gson = "2.8.5"
        const val converterGson = "2.6.0"
        const val okHttp = "3.12.0"
        const val googlePlaces = "2.5.0"
    }

    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAuth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    const val firebaseDatabase = "com.google.firebase:firebase-database:${Versions.firebaseDatabase}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebasePerformance = "com.google.firebase:firebase-perf-ktx"

    const val googlePlaces = "com.google.android.libraries.places:places:${Versions.googlePlaces}"

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:" +
            Versions.constraintLayout
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.ktxFragment}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:" +
            Versions.coroutines
    const val coroutinesPlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:" +
            Versions.coroutines
    const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:" +
            Versions.serialization

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val koinKotlin = "io.insert-koin:koin-core:${Versions.koin}"
    private const val koinTest = "io.insert-koin:koin-test:${Versions.koin}"
    private const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    private const val koinAndroidx = "io.insert-koin:koin-androidx-scope:${Versions.koin}"
    private const val koinViewModel = "io.insert-koin:koin-androidx-viewmodel:${Versions.koin}"
    private const val koinFragment = "io.insert-koin:koin-androidx-fragment:${Versions.koin}"

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