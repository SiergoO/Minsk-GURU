package com.minsk.guru.utils

interface TaskProvider<P : Any, R : Any> {
    fun provide(param: P): Task<P, R>
}