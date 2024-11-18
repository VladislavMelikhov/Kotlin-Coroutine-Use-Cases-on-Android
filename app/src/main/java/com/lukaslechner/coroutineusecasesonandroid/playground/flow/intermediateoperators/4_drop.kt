package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediateoperators

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    (1..5)
        .asFlow()
        .dropWhile { it < 3 }
        .collect { value ->
            println(value)
        }
}
