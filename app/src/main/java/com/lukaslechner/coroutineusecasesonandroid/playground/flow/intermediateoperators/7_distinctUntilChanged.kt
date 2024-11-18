package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediateoperators

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(1, 2, 2, 3, 3, 3, 4)
        .distinctUntilChanged()
        .collect { value ->
            println(value)
        }
}
