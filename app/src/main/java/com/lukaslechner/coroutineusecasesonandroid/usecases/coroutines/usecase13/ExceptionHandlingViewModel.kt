package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)
            } catch (exception: Exception) {
                if (exception is HttpException) {
                    if (exception.code() == 500) {
                        // Error Message 1
                    } else {
                        // Error Message 2
                    }
                }
                uiState.value = UiState.Error("Network request failed: $exception")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        uiState.value = UiState.Loading

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = UiState.Error("Network Request failed!")
        }

        viewModelScope.launch(exceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading

        viewModelScope.launch {

            supervisorScope {
                val oreoFeaturesDeferred = async {
                    api.getAndroidVersionFeatures(27)
                }

                val pieFeaturesDeferred = async {
                    api.getAndroidVersionFeatures(28)
                }

                val android10FeaturesDeferred = async {
                    api.getAndroidVersionFeatures(29)
                }

                val oreoFeatures = try {
                    oreoFeaturesDeferred.await()
                } catch (e: Exception) {
                    Timber.e("Error loading oreo features $e")
                    null
                }

                val pieFeatures = try {
                    pieFeaturesDeferred.await()
                } catch (e: Exception) {
                    Timber.e("Error loading pie features $e")
                    null
                }

                val android10Features = try {
                    android10FeaturesDeferred.await()
                } catch (e: Exception) {
                    Timber.e("Error loading android10 features $e")
                    null
                }

                val versionFeatures =
                    listOfNotNull(oreoFeatures, pieFeatures, android10Features)

                uiState.value = UiState.Success(versionFeatures)

            }
        }
    }
}