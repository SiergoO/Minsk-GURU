package com.minsk.guru.utils

import com.minsk.guru.domain.usecase.Cancellable
import com.minsk.guru.domain.usecase.SingleResultUseCase

fun <P : Any, R : Any> singleResultUseCaseTaskProvider(useCaseProvider: (P) -> SingleResultUseCase<P, R>): TaskProvider<P, R> =
    object : TaskProvider<P, R> {
        override fun provide(param: P): Task<P, R> = useCaseProvider(param).asTask()
    }

fun <P : Any, R : Any> SingleResultUseCase<P, R>.asTask(): Task<P, R> =
    object : Task<P, R> {
        override fun execute(param: P, callback: Task.Callback<R>): Cancellable =
            this@asTask.execute(param, object : SingleResultUseCase.Callback<R> {
                override fun onComplete(result: R) {
                    callback.onResult(result)
                    callback.onComplete()
                }

                override fun onError(error: Throwable) {
                    callback.onError(error)
                }
            })
    }