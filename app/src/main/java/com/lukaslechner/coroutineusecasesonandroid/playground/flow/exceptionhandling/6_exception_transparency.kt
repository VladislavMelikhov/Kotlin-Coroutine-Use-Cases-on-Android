package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flow {
        for (i in 1..5) {
            println("Before emit $i")
            emit(i)
            println("After emit $i")
        }
    }
        .catch { e ->
            println("Catch exception in catch() operator: $e")
        }
        .map { value ->
            if (value == 3) throw Exception("Error in map")
            value
        }
        .collect { value ->
            println("Collect: $value")
        }
}
