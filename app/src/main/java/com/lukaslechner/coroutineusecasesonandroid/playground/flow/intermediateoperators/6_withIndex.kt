package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediateoperators

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    (1..5)
        .asFlow()
        .withIndex()
        .collect { value ->
            println(value)
        }
}
