package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {

    val job = launch(Dispatchers.Default) {

        repeat(10) { index ->
            if (isActive) {
                println("operation number $index")
                Thread.sleep(100)
            } else {
                withContext(NonCancellable) {
                    delay(100)
                }
                println("Cleaning up ... ")
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("Cancelling Coroutine")
    job.cancel()
}