package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminaloperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.lastOrNull
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
        val value = flow.lastOrNull()
        println("Received value: $value")
    }
}
