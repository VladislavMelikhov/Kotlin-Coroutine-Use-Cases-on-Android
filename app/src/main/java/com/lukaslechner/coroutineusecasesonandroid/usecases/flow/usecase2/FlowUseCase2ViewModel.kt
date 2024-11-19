package com.lukaslechner.coroutineusecasesonandroid.usecases.flow.usecase2

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.flow.mock.Stock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex
import timber.log.Timber

private const val GOOGLE_STOCK_NAME = "Alphabet (Google)"
private const val GOOGLE_PRICE_THRESHOLD = 2300
private const val US_COUNTRY_NAME = "United States"
private const val BIGGEST_COMPANIES_RANK_THRESHOLD = 10
private const val MAX_LIST_UPDATES_COUNT = 10
private const val TAG = "exercise1"

class FlowUseCase2ViewModel(
    stockPriceDataSource: StockPriceDataSource,
    defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<UiState>() {

    /*

    Flow exercise 1 Goals
        1) only update stock list when Alphabet(Google) (stock.name ="Alphabet (Google)") stock price is > 2300$
        2) only show stocks of "United States" (stock.country == "United States")
        3) show the correct rank (stock.rank) within "United States", not the world wide rank
        4) filter out Apple  (stock.name ="Apple") and Microsoft (stock.name ="Microsoft"), so that Google is number one
        5) only show company if it is one of the biggest 10 companies of the "United States" (stock.rank <= 10)
        6) stop flow collection after 10 emissions from the dataSource
        7) log out the number of the current emission so that we can check if flow collection stops after exactly 10 emissions
        8) Perform all flow processing on a background thread

     */

    val currentStockPriceAsLiveData: LiveData<UiState> =
        stockPriceDataSource
            .latestStockList
            .filter { stocks ->
                stocks.isGoogleHighPrice()
            }
            .map { stocks -> stocks
                .filterUSCompanies()
                .makeGoogleNumberOne()
                .updateRanks()
                .filterBiggestCompanies()
            }
            .take(MAX_LIST_UPDATES_COUNT)
            .logEmissionIndex()
            .flowOn(defaultDispatcher)
            .map(UiState::Success)
            .onStart<UiState> {
                emit(UiState.Loading)
            }
            .asLiveData(viewModelScope.coroutineContext)
}

private fun List<Stock>.isGoogleHighPrice(): Boolean {
    val google = firstOrNull { stock -> stock.name == GOOGLE_STOCK_NAME }
    if (google == null) {
        return false
    }

    val googlePrice = google.currentPrice
    Timber.tag(TAG).d("googlePrice = $googlePrice")

    return googlePrice > GOOGLE_PRICE_THRESHOLD
}

private fun List<Stock>.filterUSCompanies(): List<Stock> =
    filter { stock ->
        stock.country == US_COUNTRY_NAME
    }

private fun List<Stock>.makeGoogleNumberOne(): List<Stock> =
    dropWhile { stock ->
        stock.name != GOOGLE_STOCK_NAME
    }

private fun List<Stock>.filterBiggestCompanies(): List<Stock> =
    filter { stock ->
        stock.rank <= BIGGEST_COMPANIES_RANK_THRESHOLD
    }

private fun List<Stock>.updateRanks(): List<Stock> =
    mapIndexed { index, stock ->
        stock.copy(rank = index + 1)
    }

private fun <T> Flow<T>.logEmissionIndex(): Flow<T> =
    withIndex()
        .onEach { indexedValue ->
            Timber.tag(TAG).d("emissionIndex = ${indexedValue.index}")
        }
        .map { indexedValue ->
            indexedValue.value
        }
