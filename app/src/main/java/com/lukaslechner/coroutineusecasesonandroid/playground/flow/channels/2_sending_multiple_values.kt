package com.lukaslechner.coroutineusecasesonandroid.playground.flow.channels

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val channel = produce {
        for (i in 1..100) {
            val value = i * 10
            println("send: $value")
            send(value)
        }
    }
    launch {
        channel.consumeEach { value ->
            println("(A) consumeEach: $value")
        }
    }
    launch {
        channel.consumeEach { value ->
            println("(B) consumeEach: $value")
        }
    }
}
