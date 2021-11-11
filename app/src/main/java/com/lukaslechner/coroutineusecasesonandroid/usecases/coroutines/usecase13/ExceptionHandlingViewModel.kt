package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import retrofit2.HttpException

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

    }

    fun showResultsEvenIfChildCoroutineFails() {

    }
}