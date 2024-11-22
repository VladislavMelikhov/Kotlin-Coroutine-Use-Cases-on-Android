package com.lukaslechner.coroutineusecasesonandroid.playground.flow.stateflow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val stateFlow = MutableStateFlow(0)
    println("state: ${stateFlow.value}, thread = ${Thread.currentThread().name}")

    val job = launch(Dispatchers.Default) {
        repeat(10_000) { i ->
            launch {
                stateFlow.value = stateFlow.value + 1
                //stateFlow.update { value -> value + 1 }
                println("iteration: $i, thread = ${Thread.currentThread().name}")
            }
        }
    }
    job.join()

    println("state: ${stateFlow.value}, thread = ${Thread.currentThread().name}")
}
