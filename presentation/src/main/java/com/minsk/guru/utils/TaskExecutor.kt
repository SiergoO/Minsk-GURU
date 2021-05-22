package com.minsk.guru.utils

import kotlinx.coroutines.CoroutineScope

interface TaskExecutor<P : Any, R : Any> {
    var resultHandler: ((R) -> Unit)?
    var completeHandler: (() -> Unit)?
    var errorHandler: ((Throwable) -> Unit)?
    val isRunning: Boolean

    fun start(param: P): Boolean
    fun stop(): Boolean

    companion object {
        fun <P : Any, R : Any> create(
            coroutineScope: CoroutineScope,
            taskProvider: TaskProvider<P, R>
        ): TaskExecutor<P, R> =
            CoroutineTaskExecutor(coroutineScope) { param ->
                taskProvider.provide(param)
            }
    }
}

fun <P : Any, R : Any> createTaskExecutor(
    coroutineScope: CoroutineScope,
    taskProvider: TaskProvider<P, R>,
    resultHandler: ((R) -> Unit)? = null,
    errorHandler: ((error: Throwable) -> Unit)? = null,
    completeHandler: (() -> Unit)? = null
): TaskExecutor<P, R> =
    (CoroutineTaskExecutor<P, R>(coroutineScope) { param ->
        taskProvider.provide(param)
    }).apply {
        this.resultHandler = resultHandler
        this.errorHandler = errorHandler
        this.completeHandler = completeHandler
    }
