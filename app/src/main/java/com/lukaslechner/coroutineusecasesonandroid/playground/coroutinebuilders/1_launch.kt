package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job: Job = launch(start = CoroutineStart.LAZY) {
            networkRequest()
            println("result received ${Thread.currentThread().name}")
        }
        delay(200)
        job.start()
        println("end of runBlocking ${Thread.currentThread().name}")
    }
}

suspend fun networkRequest(): String {
    delay(500)
    return "Result"
}