package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val job: Job = scope.launch {
        delay(100)
        println("Coroutine completed")
    }

    job.invokeOnCompletion { cause: Throwable? ->
        if (cause is CancellationException) {
            println("Coroutine was cancelled")
        }
    }

    delay(50)
    onDestroy()
}

fun onDestroy() {
    println("Life-time of scope ends")
    scope.cancel()
}