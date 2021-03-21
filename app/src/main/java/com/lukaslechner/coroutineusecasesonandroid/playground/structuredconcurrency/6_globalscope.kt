package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job

fun main() {

    println("Job of GlobalScope: ${GlobalScope.coroutineContext[Job]}")
}