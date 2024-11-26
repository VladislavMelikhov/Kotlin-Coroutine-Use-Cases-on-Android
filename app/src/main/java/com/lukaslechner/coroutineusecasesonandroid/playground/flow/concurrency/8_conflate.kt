package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val flow = flow {
        for (i in 1..5) {
            println("(dad) start cooking $i, thread = ${Thread.currentThread().name}")
            delay(2_000)
            println("(dad) finish cooking $i, thread = ${Thread.currentThread().name}")
            emit(i)
        }
    }
        .conflate()

    flow.collect { value ->
        println("(son) start eating $value, thread = ${Thread.currentThread().name}")
        delay(5_000)
        println("(son) finish eating $value, thread = ${Thread.currentThread().name}")
    }
}
