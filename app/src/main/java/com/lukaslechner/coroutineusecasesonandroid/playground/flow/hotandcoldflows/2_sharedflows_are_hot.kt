package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hotandcoldflows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

fun main() {

    val sharedFlow = MutableSharedFlow<Int>()

    val scope = CoroutineScope(EmptyCoroutineContext)

    scope.launch {
        for (i in 1..5) {
            println("SharedFlow emits: $i")
            sharedFlow.emit(i)
            delay(1_000)
        }
    }

    scope.launch {
        sharedFlow.collect { value ->
            println("(A) collect: $value")
        }
    }

    scope.launch {
        sharedFlow.collect { value ->
            println("(B) collect: $value")
        }
    }

    Thread.sleep(6_000)
}
