package com.example.books.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.books.Book
import com.example.books.network.BooksApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private val _searchResult = MutableStateFlow(emptyList<Book>())
    val searchResult = _searchResult.asStateFlow()

    fun onSearch(value: String) {
        _search.update { value }
    }

    fun onClick() {
        viewModelScope.launch {
            val result = BooksApi.retrofitService.getBooks(search.value)
            _searchResult.update { result }
        }
    }
}
