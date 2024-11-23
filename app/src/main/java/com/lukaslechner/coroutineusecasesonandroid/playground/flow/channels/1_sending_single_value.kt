package com.lukaslechner.coroutineusecasesonandroid.playground.flow.channels

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val deferred = async {
        delay(1_000)
        123
    }
    launch {
        val result = deferred.await()
        println(result)
    }
}
