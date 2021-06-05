package com.minsk.guru.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@Suppress("EXPERIMENTAL_API_USAGE")
internal class CoroutineTaskExecutor<P : Any, R : Any>(
    private val coroutineScope: CoroutineScope,
    private val taskProvider: (P) -> Task<P, R>
) : TaskExecutor<P, R> {

    @Volatile
    override var resultHandler: ((R) -> Unit)? = null

    @Volatile
    override var completeHandler: (() -> Unit)? = null

    @Volatile
    override var errorHandler: ((Throwable) -> Unit)? = null
    override val isRunning: Boolean
        get() = synchronized(lock) { null != taskContext }
    private val lock = Any()

    @Volatile
    private var taskContext: TaskContext? = null

    override fun start(param: P): Boolean =
        synchronized(lock) {
            if (null == taskContext) {
                val context = TaskContext(null).also { taskContext = it }
                context.job = coroutineScope.launch {
                    try {
                        taskProvider(param)
                            .execute(param)
                            .collect { onTaskResult(context, it) }
                        onTaskComplete(context)
                    } catch (error: Throwable) {
                        val cause = error.cause
                        when {
                            error !is CancellationException -> {
                                onTaskError(context, error)
                            }
                            null != cause -> {
                                onTaskError(context, cause)
                            }
                            else -> {
                                onTaskCancelled(context)
                            }
                        }
                    }
                }
                true
            } else false
        }

    override fun stop(): Boolean =
        synchronized(lock) {
            val context = taskContext
            if (null != context) {
                taskContext = null
                context.job?.cancel()
                true
            } else false
        }

    private fun onTaskResult(ctx: TaskContext, result: R) {
        synchronized(lock) {
            if (ctx === taskContext) Unit
            else null
        }?.also {
            resultHandler?.invoke(result)
        }
    }

    private fun onTaskComplete(ctx: TaskContext) {
        synchronized(lock) {
            if (ctx === taskContext) {
                taskContext = null
            } else null
        }?.also {
            completeHandler?.invoke()
        }
    }

    private fun onTaskError(ctx: TaskContext, error: Throwable) {
        synchronized(lock) {
            if (ctx === taskContext) {
                taskContext = null
            } else null
        }?.also {
            errorHandler?.invoke(error)
        }
    }

    private fun onTaskCancelled(ctx: TaskContext) {
        synchronized(lock) {
            if (ctx === taskContext) {
                taskContext = null
            } else null
        }
    }

    private fun <P : Any, R : Any> Task<P, R>.execute(param: P): Flow<R> =
        callbackFlow {
            val cancellable = this@execute.execute(param, object : Task.Callback<R> {
                override fun onResult(result: R) {
                    try {
                        sendBlocking(result)
                    } catch (error: Exception) {
                        // ignore
                    }
                }

                override fun onComplete() {
                    channel.close()
                }

                override fun onError(error: Throwable) {
                    cancel("Error occurred.", error)
                }
            })
            awaitClose { cancellable.cancel() }
        }

    private class TaskContext(
        @Volatile
        var job: Job?
    )
}
