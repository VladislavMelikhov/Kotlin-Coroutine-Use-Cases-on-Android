package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    val stocksFlow = stocksFlow()

    stocksFlow
        .onCompletion { cause ->
            if (cause == null) {
                println("Completed, success")
            } else {
                println("Completed, exception: $cause")
            }
        }
        .onEach { stock ->
            throw Exception("Exception in collect")
            println("Collected: $stock")
        }
        .catch { e ->
            println("Handle exception in catch() operator: $e")
        }
        .launchIn(this)
}

private fun stocksFlow(): Flow<String> =
    flow {
        emit("Apple")
        emit("Microsoft")
        throw Exception("Network request failed")
    }
