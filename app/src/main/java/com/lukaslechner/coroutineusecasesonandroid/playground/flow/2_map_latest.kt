package com.lukaslechner.coroutineusecasesonandroid.playground.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    flow<Long> {
        emit(1)
        emit(2)
        emit(3)
    }
        .onEach { println(it) }
        .mapLatest { wait(it) }
        .collect()
}

suspend fun wait(x: Long) {
    println("before delay")
    delay(1000)
    println("after delay")
}