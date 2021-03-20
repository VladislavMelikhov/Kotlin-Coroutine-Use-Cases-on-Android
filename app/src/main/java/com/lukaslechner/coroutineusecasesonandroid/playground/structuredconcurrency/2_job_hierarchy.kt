package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {

    val scopeJob: Job = Job()
    val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + scopeJob)

    val passedJob: Job = Job()
    val coroutineJob: Job = scope.launch(passedJob) {
        println("Starting coroutine")
        delay(1000)
    }

    println("passedJob === coroutineJob = ${passedJob === coroutineJob}")

    println("Is coroutineJob a child of scopeJob = ${scopeJob.children.contains(coroutineJob)}")
}