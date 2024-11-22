package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

private const val TAG = "ExposeFlowInVM"

class FlowUseCase4ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPrice: StateFlow<UiState> = stockPriceDataSource
        .latestStockList
        .map(UiState::Success)
        .onStart {
            Timber.tag(TAG).d("Flow started")
        }
        .onCompletion {
            Timber.tag(TAG).d("Flow completed")
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UiState.Loading,
        )
}
