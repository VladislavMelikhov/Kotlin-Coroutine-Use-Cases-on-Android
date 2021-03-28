package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    val job = launch {
        repeat(10) { index ->
            println("operation number $index")
            try {
                delay(100)
            } catch (exception: CancellationException) {
                println("CancellationException was thrown")
                throw exception
            }
        }
    }

    delay(250)
    println("Cancelling Coroutine")
    job.cancel()
}