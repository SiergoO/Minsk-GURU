package com.minsk.guru.ui

import android.app.Application
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MinskGuruApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startPresentationKoin(this@MinskGuruApplication)
    }
}