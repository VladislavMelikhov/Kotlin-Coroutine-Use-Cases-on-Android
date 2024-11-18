package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediateoperators

import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(1, '2', 3, "4", 5.0)
        .filterIsInstance<Int>()
        .collect { value ->
            println(value)
        }
}
