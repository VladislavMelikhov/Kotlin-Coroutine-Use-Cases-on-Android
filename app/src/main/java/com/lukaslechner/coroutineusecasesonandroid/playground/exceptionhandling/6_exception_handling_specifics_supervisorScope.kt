package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)

    scope.launch {
        try {
            supervisorScope {
                launch {
                    println("CEH: ${coroutineContext[CoroutineExceptionHandler]}")
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    Thread.sleep(100)
}