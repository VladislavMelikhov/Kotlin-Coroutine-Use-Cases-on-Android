package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        val timeout = 1000L
        val numberOfRetries = 2

        viewModelScope.launch {

            try {
                val versionFeatures = listOf(27, 28)
                    .map { apiLevel ->
                        async {
                            retryWithTimeout(numberOfRetries, timeout) {
                                api.getAndroidVersionFeatures(apiLevel)
                            }
                        }
                    }
                    .awaitAll()

                uiState.value = UiState.Success(versionFeatures)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Error message")
            }
        }
    }

    private suspend fun <T : Any> retryWithTimeout(
        numberOfRetries: Int,
        timeout: Long,
        block: suspend () -> T
    ): T = retry(numberOfRetries) {
        withTimeout(timeout) {
            block()
        }
    }

    private suspend fun <T : Any> retry(
        numberOfRetries: Int,
        delayBetweenRetries: Long = 100,
        block: suspend () -> T
    ): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (exception: Exception) {
                Timber.e(exception)
            }
            delay(delayBetweenRetries)
        }
        return block()
    }
}