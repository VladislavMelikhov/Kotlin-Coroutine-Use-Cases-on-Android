package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    launch {
        (1..5)
            .asFlow()
            .cancellable()
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
