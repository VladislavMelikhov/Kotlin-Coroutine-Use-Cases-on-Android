package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase4

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

private const val TAG = "ExposeFlowInVM"

class FlowUseCase4ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPrice: Flow<UiState> = stockPriceDataSource
        .latestStockList
        .map(UiState::Success)
        .onStart<UiState> {
            Timber.tag(TAG).d("Flow started")
            emit(UiState.Loading)
        }
        .onCompletion {
            Timber.tag(TAG).d("Flow completed")
        }
}
