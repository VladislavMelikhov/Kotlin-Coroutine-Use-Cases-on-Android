package com.lukaslechner.coroutineusecasesonandroid.playground.flow


import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        integers(1)
            .take(20)
            .zip(fibonacci()) { i, x -> "$i is $x"}
            .collect { x -> println(x) }
    }
}

fun fibonacci(): Flow<Int> =
    flow {
        var x1 = 0
        var x2 = 1
        while (true) {
            val x = x1
            emit(x)
            x1 = x2
            x2 += x
        }
    }

fun integers(initial: Int = 0): Flow<Int> =
    flow {
        var x = initial
        while (true) {
            emit(x)
            x++
        }
    }