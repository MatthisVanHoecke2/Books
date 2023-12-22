package com.example.books.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.example.books.network.BooksApi
import com.example.books.ui.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : CustomViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun onSearch(value: String) {
        _homeUiState.update { it.copy(search = value) }
    }

    fun searchApi() {
        onLoadChange(true)
        viewModelScope.launch {
            val result = BooksApi.retrofitService.getBooks(homeUiState.value.search)
            _homeUiState.update { it.copy(searchResult = result.docs.filter { book -> book.coverId != null }) }
            onLoadChange(false)
        }
    }
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
