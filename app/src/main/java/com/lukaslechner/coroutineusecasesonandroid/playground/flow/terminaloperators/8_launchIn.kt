package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminaloperators

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

fun main() {

    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }

    val scope = CoroutineScope(EmptyCoroutineContext)

    flow
        .onEach { println("Received $it in launchIn (A)") }
        .launchIn(scope)

    flow
        .onEach { println("Received $it in launchIn (B)") }
        .launchIn(scope)

    Thread.sleep(1_000)
    println("----------")

    scope.launch {
        flow.collect { println("Received $it in collect (A)") }
        flow.collect { println("Received $it in collect (B)") }
    }

    Thread.sleep(1_000)
}
