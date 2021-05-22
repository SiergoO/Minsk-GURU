package com.minsk.guru.data.firebase

import com.google.android.gms.tasks.Task

fun <T> Task<T>.process(): T =
    when {
        this.isCanceled -> { throw exception as Throwable }
        else -> result
    }

