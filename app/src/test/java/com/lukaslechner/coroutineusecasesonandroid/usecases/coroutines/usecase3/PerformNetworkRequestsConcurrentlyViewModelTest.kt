package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.utils.MainCoroutineScopeRule
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PerformNetworkRequestsConcurrentlyViewModelTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performNetworkRequestsSequentially() should load data sequentially`() = mainCoroutineScopeRule.runBlockingTest {

        //Arrange
        val responseDelay = 1000L
        val fakeApi = FakeSuccessApi(responseDelay)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        observeViewModel(viewModel)

        //Act
        viewModel.performNetworkRequestsSequentially()
        val forwardedTime = advanceUntilIdle()

        //Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ),
            receivedUiStates
        )
        assertEquals(3000, forwardedTime)
    }

    @Test
    fun `performNetworkRequestsConcurrently() should load data concurrently`() = mainCoroutineScopeRule.runBlockingTest {

        //Arrange
        val responseDelay = 1000L
        val fakeApi = FakeSuccessApi(responseDelay)
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(fakeApi)
        observeViewModel(viewModel)

        //Act
        viewModel.performNetworkRequestsConcurrently()
        val forwardedTime = advanceUntilIdle()

        //Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ),
            receivedUiStates
        )
        assertEquals(1000, forwardedTime)
    }

    private fun observeViewModel(viewModel: PerformNetworkRequestsConcurrentlyViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}