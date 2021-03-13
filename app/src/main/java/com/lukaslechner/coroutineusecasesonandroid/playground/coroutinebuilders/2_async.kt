package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()

    val deferred1: Deferred<String> = async(start = CoroutineStart.LAZY) {
        val result1 = networkCall(1)
        printResult(result1, startTime)
        result1
    }

    val deferred2: Deferred<String> = async {
        val result2 = networkCall(2)
        printResult(result2, startTime)
        result2
    }

    deferred1.start()

    val resultList: List<String> = listOf(
        deferred1.await(),
        deferred2.await()
    )

    printResult(resultList, startTime)
}

suspend fun networkCall(number: Int): String {
    delay(500)
    return "Result $number"
}

fun printResult(result: Any, startTime: Long) {
    println("result received: $result after ${elapsedMillis(startTime)}ms on ${Thread.currentThread().name}")
}

fun elapsedMillis(startTime: Long): Long =
    System.currentTimeMillis() - startTime
