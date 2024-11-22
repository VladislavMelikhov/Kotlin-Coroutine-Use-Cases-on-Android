package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber

private const val TAG = "ExposeFlowInVM"

class FlowUseCase4ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPrice: SharedFlow<UiState> = stockPriceDataSource
        .latestStockList
        .map(UiState::Success)
        .onStart<UiState> {
            Timber.tag(TAG).d("Flow started")
            emit(UiState.Loading)
        }
        .onCompletion {
            Timber.tag(TAG).d("Flow completed")
        }
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            replay = 1,
        )
}
