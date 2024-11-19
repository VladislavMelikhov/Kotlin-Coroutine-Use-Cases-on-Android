package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flow {
        emit(1)
        emit(2)
        emit(3)
    }
        .collect { value ->
            println("Collect: $value")
        }
}

val inlinedFlow = flow<Int> {
    println("Collect: 1")
    println("Collect: 2")
    println("Collect: 3")
}
