package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger

fun main(): Unit = runBlocking {

    launch {
        intFlow()
            .onCompletion { cause ->
                println("onCompletion: $cause")
            }
            .catch { e ->
                println("catch() operator: $e")
            }
            .collect { value ->
                println("collect: $value")
                if (value == 3) {
                    cancel()
                }
            }
    }
}

private fun intFlow(): Flow<Int> =
    flow {
        for (i in 1..3) {
            delay(1_000)
            emit(i)
        }

        println("Start factorial calculation")
        val factorial = calculateFactorialOf(1_000)
        println("Finish factorial calculation")
        emit(factorial.toInt())
    }

private suspend fun calculateFactorialOf(number: Int): BigInteger =
    coroutineScope {
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            ensureActive()
            delay(10)
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        factorial
    }
