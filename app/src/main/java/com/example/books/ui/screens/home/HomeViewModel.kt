package com.example.books.ui.screens.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.books.BooksApplication
import com.example.books.repository.BookRepository
import com.example.books.ui.CustomViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel class for Home page
 * */
class HomeViewModel(private val booksRepository: BookRepository) : CustomViewModel() {
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
     * Queries the API for books based on the search value,
     * and loads the filtered list into the [homeUiState]
     * */
    fun searchApi() {
        onLoadChange(true)
        viewModelScope.launch {
            val result = booksRepository.getBooks(homeUiState.value.search)
            _homeUiState.update { it.copy(currentPage = 0, searchResult = result) }
            onLoadChange(false)
        }
    }

    /**
     * Expands loaded list with new items
     * @param limit limit total amount of requested items
     * */
    fun expandSearch(limit: Long = 25) {
        _homeUiState.update { it.copy(currentPage = it.currentPage + 1) }
        onLoadChange(true)
        viewModelScope.launch {
            val result = booksRepository.getBooks(homeUiState.value.search, offset = _homeUiState.value.currentPage * limit, limit = limit)
            val list = homeUiState.value.searchResult.toMutableList()
            list.addAll(result)
            _homeUiState.update { it.copy(searchResult = list) }
            onLoadChange(false)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                HomeViewModel(booksRepository)
            }
        }
    }
}
