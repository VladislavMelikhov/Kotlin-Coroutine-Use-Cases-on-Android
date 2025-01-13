package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.*

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("[ExceptionHandler] Caught $throwable")
    }
    val viewModelScope = CoroutineScope(Job() + exceptionHandler)

//    val deferredList: List<Deferred<String>> =
//        (1..3)
//            .map { number ->
//                viewModelScope.async {
//                    httpRequest(number)
//                }
//            }
//
//    viewModelScope.launch {
//        deferredList
//            .map { deferred ->
//                val result = try {
//                    deferred.await()
//                } catch (e: Exception) {
//                    println("Caught $e in try-catch")
//                    if (e is CancellationException) throw e
//                    "No result"
//                }
//                result
//            }
//            .let { result ->
//                println("received: $result")
//            }
//    }


    viewModelScope.launch {
        try {
            val res = useCase()
            println("Res: $res")
        } catch (e: Exception) {
            println("[main] Caught $e in try-catch")
            if (e is CancellationException) throw e
        }
    }

    Thread.sleep(10_000)
}

private suspend fun useCase(): List<String> =
    coroutineScope {
        val deferredList: List<Deferred<String>> =
            (1..3)
                .map { number ->
                    async {
                        httpRequest(number)
                    }
                }

        deferredList.map { deferred ->
            val result = try {
                deferred.await()
            } catch (e: Exception) {
                println("[UseCase] Caught $e in try-catch")
                if (e is CancellationException) throw e
                "No result"
            }
            result
        }
    }

private suspend fun httpRequest(number: Int): String {
    println("--> begin request $number")
    delay(number * 1_000L)
    if (number == 2) {
        throw RuntimeException("Network request fails")
    }
    println("<-- end request $number")
    return "Result $number"
}
