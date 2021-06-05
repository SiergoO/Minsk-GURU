package com.minsk.guru.utils

interface TaskExecutor<P : Any, R : Any> {
    var resultHandler: ((R) -> Unit)?
    var completeHandler: (() -> Unit)?
    var errorHandler: ((Throwable) -> Unit)?
    val isRunning: Boolean

    fun start(param: P): Boolean
    fun stop(): Boolean
}