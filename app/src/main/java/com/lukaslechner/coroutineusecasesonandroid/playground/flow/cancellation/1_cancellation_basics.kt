package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    launch {
        intFlow()
            .onCompletion { cause ->
                println("onCompletion: $cause")
            }
            .catch { e ->
                println("catch() operator: $e")
            }
            .collect { value ->
                println("collect: $value")
                if (value == 3) {
                    cancel()
                }
            }
    }
}

private fun intFlow(): Flow<Int> =
    flow {
        for (i in 1..5) {
            delay(1_000)
            emit(i)
        }
    }
