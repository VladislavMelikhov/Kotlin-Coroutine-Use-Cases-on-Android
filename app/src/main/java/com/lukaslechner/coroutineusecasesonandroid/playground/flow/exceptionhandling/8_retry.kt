package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    stocksFlow()
        .catch { e ->
            println("Catch exception in catch() operator: $e")
        }
        .collect { value ->
            println("Collect: $value")
        }
}

private fun stocksFlow(): Flow<String> =
    flow {
        for (i in 1..5) {
            delay(1_000)

            if (i < 5) {
                emit("Stock $i")
            } else {
                throw NetworkException("Network request failed")
            }
        }
    }
        .retryWhen { cause, attemptIndex ->
            val attemptNumber = attemptIndex + 1
            println("Retry $attemptNumber, cause: $cause")
            delay(1_000 * attemptNumber)
            cause is NetworkException
        }

private class NetworkException(message: String) : Exception(message)
