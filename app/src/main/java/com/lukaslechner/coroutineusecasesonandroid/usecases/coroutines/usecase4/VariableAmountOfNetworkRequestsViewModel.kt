package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import kotlinx.coroutines.*

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersions: List<AndroidVersion> = mockApi.getRecentAndroidVersions()
                val versionFeatures: List<VersionFeatures> = recentVersions.map { androidVersion ->
                    mockApi.getAndroidVersionFeatures(androidVersion.apiLevel)
                }
                uiState.value = UiState.Success(versionFeatures)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val recentVersions: List<AndroidVersion> = mockApi.getRecentAndroidVersions()
                val versionFeaturesDeferred: List<Deferred<VersionFeatures>> = recentVersions.map { androidVersion ->
                    async {
                        mockApi.getAndroidVersionFeatures(androidVersion.apiLevel)
                    }
                }
                val versionFeatures: List<VersionFeatures> = versionFeaturesDeferred.awaitAll()
                uiState.value = UiState.Success(versionFeatures)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }
}