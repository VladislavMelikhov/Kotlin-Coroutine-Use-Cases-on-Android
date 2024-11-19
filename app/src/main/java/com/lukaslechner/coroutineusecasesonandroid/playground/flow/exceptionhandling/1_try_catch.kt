package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val stocksFlow = stocksFlow()
        .map {
            throw Exception("Exception in map")
        }

    try {
        stocksFlow
            .onCompletion { cause ->
                if (cause == null) {
                    println("Completed, success")
                } else {
                    println("Completed, exception: $cause")
                }
            }
            .collect { stock ->
                println("Collected: $stock")
            }
    } catch (e: Throwable) {
        println("Handle exception in catch block: $e")
    }
}

private fun stocksFlow(): Flow<String> =
    flow {
        emit("Apple")
        emit("Microsoft")
        throw Exception("Network request failed")
    }
