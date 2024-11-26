package com.lukaslechner.coroutineusecasesonandroid.playground.flow.concurrency

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(): Unit = runBlocking {

    val flow = MutableSharedFlow<Int>(replay = 5)
    val startTime = System.currentTimeMillis()

    launch {
        flow.collect { value ->
            printWithTimePassed("collector 1: $value", startTime)
            delay(1_000)
        }
    }

    launch {
        flow.collect { value ->
            printWithTimePassed("collector 2: $value", startTime)
            delay(2_000)
        }
    }

    launch {
        val timeToEmit = measureTimeMillis {
            for (i in 1..5) {
                delay(1_000)
                flow.emit(i)
            }
        }
        printWithTimePassed("Time to emit all values: $timeToEmit ms", startTime)
    }
}
