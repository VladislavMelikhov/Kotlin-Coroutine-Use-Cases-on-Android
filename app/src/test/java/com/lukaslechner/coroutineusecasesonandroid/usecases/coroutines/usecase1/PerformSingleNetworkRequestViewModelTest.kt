package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.MainCoroutineScopeRule
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PerformSingleNetworkRequestViewModelTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success when network request is successful`() {

        //Arrange
        val fakeApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        //Act
        viewModel.performSingleNetworkRequest()

        //Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )
    }

    @Test
    fun `should return Error when network request fails`() {
        //Arrange
        val fakeApi = FakeErrorApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        //Act
        viewModel.performSingleNetworkRequest()

        //Assert
        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed!")
            ),
            receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}