package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPriceAsLiveData: LiveData<UiState> =
        stockPriceDataSource
            .latestStockList
            .map(UiState::Success)
            .onStart<UiState> {
                emit(UiState.Loading)
            }
            .onCompletion {
                Timber.tag("StockPricesFlow").d("onCompletion")
            }
            .asLiveData(viewModelScope.coroutineContext)
}
