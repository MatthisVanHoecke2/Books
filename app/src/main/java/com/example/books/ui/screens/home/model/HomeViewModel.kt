package com.example.books.ui.screens.home.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.books.BooksApplication
import com.example.books.model.Book
import com.example.books.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Interface for determining the current state of a book CRUD operation
 * */
sealed interface BookApiState {
    /**
     * Data object for when the operation is waiting to be executed
     * */
    data object Start : BookApiState

    /**
     * Data object for when the operation was successfully executed
     * @property books the books retrieved from the repository
     * */
    data class Success(val books: List<Book>) : BookApiState

    /**
     * Data object for when the operation is loading
     * */
    data object Loading : BookApiState

    /**
     * Data class for when the operation has failed
     * @param message error message to display
     * */
    data class Error(val message: String) : BookApiState
}

/**
 * ViewModel class for Home page
 * @property booksRepository Repository to retrieve data
 * */
class HomeViewModel(private val booksRepository: BookRepository) : ViewModel() {
    var bookApiState: BookApiState by mutableStateOf(BookApiState.Start)
        private set

    private val _homeUiState = MutableStateFlow(HomeUiState())

    /**
     * Value holding the current [HomeUiState]
     * */
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
     * @param limit limit total amount of requested items
     * */
    fun searchApi(limit: Long = 25) {
        bookApiState = BookApiState.Loading
        _homeUiState.update { it.copy(searchResult = emptyList()) }
        val search = homeUiState.value.search
        viewModelScope.launch {
            bookApiState = try {
                if (homeUiState.value.search.isEmpty()) {
                    BookApiState.Start
                } else {
                    val result = booksRepository.getBooks(query = search, limit = limit)
                    _homeUiState.update { it.copy(currentPage = 0, searchResult = result, endOfList = result.size < limit) }
                    BookApiState.Success(result)
                }
            } catch (ex: IOException) {
                BookApiState.Error("An error occurred while fetching books")
            }
        }
    }

    /**
     * Expands loaded list with new items
     * @param limit limit total amount of requested items
     * */
    fun expandSearch(limit: Long = 25) {
        _homeUiState.update { it.copy(currentPage = it.currentPage + 1) }
        bookApiState = BookApiState.Loading
        viewModelScope.launch {
            bookApiState = try {
                val result = booksRepository.getBooks(homeUiState.value.search, offset = _homeUiState.value.currentPage * limit, limit = limit)
                val list = homeUiState.value.searchResult.toMutableList()
                list.addAll(result)
                _homeUiState.update { it.copy(searchResult = list, endOfList = result.size < limit) }
                BookApiState.Success(list)
            } catch (ex: IOException) {
                BookApiState.Error("An error occurred while fetching books")
            }
        }
    }

    companion object {
        /**
         * Factory object for creating HomeViewModel instance
         * */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                HomeViewModel(booksRepository)
            }
        }
    }
}
