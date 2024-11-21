package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hotandcoldflows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    launch {
        coldFlow()
            .onCompletion {
                println("(A) onCompletion")
            }
            .collect { value ->
                println("(A) collect: $value")
            }
    }

    launch {
        coldFlow()
            .onCompletion {
                println("(B) onCompletion")
            }
            .collect { value ->
                println("(B) collect: $value")
            }
    }
}

private fun coldFlow(): Flow<Int> =
    flow {
        for (i in 1..5) {
            delay(1_000)
            println("emit: $i")
            emit(i)
        }
    }
