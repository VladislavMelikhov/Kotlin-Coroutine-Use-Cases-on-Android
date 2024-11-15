package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminaloperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun main() {

    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit(1)

        delay(100)
        println("Emitting second value")
        emit(2)
    }

    val list = buildList {
        add(1)
        println("Add 1 to list")

        add(2)
        println("Add 2 to list")
    }
}
