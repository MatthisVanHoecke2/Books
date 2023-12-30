package com.example.books.viewmodels

import com.example.books.fake.FakeBooksApiService
import com.example.books.fake.FakeDataSource
import com.example.books.fake.TestDispatcherRule
import com.example.books.repository.NetworkBookRepository
import com.example.books.ui.screens.home.model.BookApiState
import com.example.books.ui.screens.home.model.HomeViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private val booksRepository = NetworkBookRepository(
        booksApiService = FakeBooksApiService(),
        checkConnection = { true },
    )

    @Test
    fun homeViewModel_getBooks_verifyBookApiState() = runTest {
        val homeViewModel = HomeViewModel(booksRepository)
        assertEquals(BookApiState.Start, homeViewModel.bookApiState)
    }

    @Test
    fun homeViewModel_onSearch_validateHomeUiStateSearch() = runTest {
        val homeViewModel = HomeViewModel(booksRepository)
        val testString = "test"
        homeViewModel.onSearch(testString)
        assertEquals(testString, homeViewModel.homeUiState.value.search)
    }

    @Test
    fun homeViewModel_searchApi_validateHomeUiStateSearchResult() = runTest {
        val homeViewModel = HomeViewModel(booksRepository)
        val testString = "test"
        homeViewModel.onSearch(testString)
        homeViewModel.searchApi()
        assertEquals(FakeDataSource.bookIndices, homeViewModel.homeUiState.value.searchResult)
    }

    @Test
    fun homeViewModel_expandSearch_validateHomeUiStateSearchResult() = runTest {
        val homeViewModel = HomeViewModel(booksRepository)
        val testString = "test"
        homeViewModel.onSearch(testString)
        homeViewModel.searchApi(limit = 1)
        assertEquals(listOf(FakeDataSource.bookIndices.first()), homeViewModel.homeUiState.value.searchResult)
        homeViewModel.expandSearch(limit = 1)
        assertEquals(FakeDataSource.bookIndices, homeViewModel.homeUiState.value.searchResult)
    }
}
