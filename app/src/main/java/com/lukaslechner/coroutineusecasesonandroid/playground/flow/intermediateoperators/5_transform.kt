package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediateoperators

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    (1..5)
        .asFlow()
        .transform {
            emit(it)
            emit(it * 10)
        }
        .collect { value ->
            println(value)
        }
}
