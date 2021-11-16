package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.utils.MainCoroutineScopeRule
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class AndroidVersionRepositoryTest {

    @get:Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    @Test
    fun `loadAndStoreRemoteAndroidVersions() should continue to load and store android versions when calling scope gets cancelled`() {
        mainCoroutineScopeRule.runBlockingTest {
            //Arrange
            val fakeDatabase = FakeDatabase()
            val fakeApi = FakeApi()

            val repository = AndroidVersionRepository(fakeDatabase, mainCoroutineScopeRule, fakeApi)

            //Act
            val testViewModelScope = TestCoroutineScope(SupervisorJob())

            testViewModelScope.launch {
                repository.loadAndStoreRemoteAndroidVersions()
                fail("Scope should be cancelled before versions are loaded")
            }

            testViewModelScope.cancel()
            advanceUntilIdle()

            //Assert
            assertEquals(true, fakeDatabase.insertedIntoDb)
        }
    }
}