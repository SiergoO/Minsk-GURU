package com.minsk.guru

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MinskGuruApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startPresentationKoin(this@MinskGuruApplication)
        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPS_API_KEY)
    }
}