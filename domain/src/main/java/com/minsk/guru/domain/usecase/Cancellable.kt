package com.minsk.guru.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

interface Cancellable {
    val isCancelled: Boolean
    fun cancel()
}

internal fun CoroutineScope.asCancellable(): Cancellable =
    object : Cancellable {
        override val isCancelled: Boolean
            get() = this@asCancellable.coroutineContext[Job]?.isCancelled ?: false

        override fun cancel() {
            this@asCancellable.cancel()
        }
    }