package com.example.books.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.example.books.network.BooksApi
import com.example.books.ui.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel class for Home page
 * */
class HomeViewModel : CustomViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    /**
     * Updates the [homeUiState] search value
     * @param value new value to update the search value to
     * */
    fun onSearch(value: String) {
        _homeUiState.update { it.copy(search = value) }
    }

    /**
     * Queries the API for books based on the search value, then filters them based on whether they have covers or not,
     * and loads the filtered list into the [homeUiState]
     * */
    fun searchApi() {
        onLoadChange(true)
        viewModelScope.launch {
            val result = BooksApi.retrofitService.getBooks(homeUiState.value.search)
            _homeUiState.update { it.copy(searchResult = result.docs.filter { book -> book.coverId != null }) }
            onLoadChange(false)
        }
    }

    /**
     * Expands loaded list with new items
     * @param offset amount of items to skip
     * @param limit limit total amount of requested items
     * */
    fun expandSearch(offset: Long = 0, limit: Long = 25) {
        onLoadChange(true)
        viewModelScope.launch {
            val result = BooksApi.retrofitService.getBooks(homeUiState.value.search, offset = offset, limit = limit)
            val list = homeUiState.value.searchResult.toMutableList()
            list.addAll(result.docs)
            _homeUiState.update { it.copy(searchResult = list.filter { book -> book.coverId != null }) }
            onLoadChange(false)
        }
    }
}
