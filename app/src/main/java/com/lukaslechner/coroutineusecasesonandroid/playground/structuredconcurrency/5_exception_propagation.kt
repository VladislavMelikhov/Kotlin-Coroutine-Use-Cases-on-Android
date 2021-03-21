package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() = runBlocking {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable")
    }

    val job: CompletableJob = SupervisorJob()
    job.invokeOnCompletion { throwable ->
        println("Scope got cancelled $throwable")
    }
    val scope = CoroutineScope(job + exceptionHandler)


    val job1 = scope.launch {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException("Test exception")
    }
    job1.invokeOnCompletion { throwable ->
        println("Coroutine 1 got cancelled $throwable")
    }

    val job2 = scope.launch {
        println("Coroutine 2 starts")
        delay(500)
        println("Coroutine 2 completed")
    }
    job2.invokeOnCompletion { throwable ->
        println("Coroutine 2 got cancelled $throwable")
    }

    delay(1000)
}