package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    private val _currentStockPriceAsLiveData: MutableLiveData<UiState> = MutableLiveData()
    val currentStockPriceAsLiveData: LiveData<UiState> = _currentStockPriceAsLiveData

    init {
        stockPriceDataSource
            .latestStockList
            .map(UiState::Success)
            .onStart<UiState> {
                emit(UiState.Loading)
            }
            .onEach { uiState ->
                _currentStockPriceAsLiveData.value = uiState
            }
            .launchIn(viewModelScope)
    }
}
