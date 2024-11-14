package com.lukaslechner.coroutineusecasesonandroid.playground.flow.builders

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val firstFlow = flowOf(1)
    firstFlow.collect { value ->
        println("firstFlow: $value")
    }

    val secondFlow = flowOf(1, 2, 3)
    secondFlow.collect { value ->
        println("secondFlow: $value")
    }

    listOf("A", "B", "C").asFlow().collect { value ->
        println("asFlow: $value")
    }

    flow {
        delay(2_000)
        emit("item emitted after delay")

        emitAll(secondFlow)
    }.collect { value ->
        println("flow{}: $value")
    }
}
