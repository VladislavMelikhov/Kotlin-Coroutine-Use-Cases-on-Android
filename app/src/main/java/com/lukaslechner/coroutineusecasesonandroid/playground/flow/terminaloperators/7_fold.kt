package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminaloperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }

    runBlocking {
        val result = flow.fold(5) { acc, value -> acc + value }
        println("Received result: $result")
    }
}
