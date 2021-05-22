package com.minsk.guru.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface TaskExecutorFactory {
    fun <P : Any, R : Any> createTaskExecutor(taskProvider: TaskProvider<P, R>): TaskExecutor<P, R>

    companion object {
        fun create(coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)): TaskExecutorFactory =
            object : TaskExecutorFactory {
                override fun <P : Any, R : Any> createTaskExecutor(taskProvider: TaskProvider<P, R>): TaskExecutor<P, R> =
                    CoroutineTaskExecutor(coroutineScope) { param ->
                        taskProvider.provide(param)
                    }
            }
    }
}

fun <P : Any, R : Any> TaskExecutorFactory.createTaskExecutor(
    taskProvider: TaskProvider<P, R>,
    resultHandler: ((R) -> Unit)? = null,
    errorHandler: ((error: Throwable) -> Unit)? = null,
    completeHandler: (() -> Unit)? = null
): TaskExecutor<P, R> =
    this@createTaskExecutor.createTaskExecutor(taskProvider).apply {
        this.resultHandler = resultHandler
        this.errorHandler = errorHandler
        this.completeHandler = completeHandler
    }
