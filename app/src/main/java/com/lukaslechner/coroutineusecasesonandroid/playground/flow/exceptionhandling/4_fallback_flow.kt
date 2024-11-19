package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val stocksFlow = stocksFlow()

    stocksFlow
        .onCompletion { cause ->
            if (cause == null) {
                println("Completed, success")
            } else {
                println("Completed, exception: $cause")
            }
        }
        .catch { e ->
            println("Handle exception in first catch() operator: $e")
            emitAll(fallbackFlow())
        }
        .catch { e ->
            println("Handle exception in second catch() operator: $e")
        }
        .collect { stock ->
            println("Collected: $stock")
        }
}

private fun stocksFlow(): Flow<String> =
    flow {
        emit("Apple")
        emit("Microsoft")
        throw Exception("Network request failed")
    }

private fun fallbackFlow(): Flow<String> =
    flow {
        emit("Fallback Stock")
        throw Exception("Fallback flow failed")
    }
