package com.example.books.ui.screens.bookdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.books.BooksApplication
import com.example.books.model.Book
import com.example.books.repository.BookRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface BookApiState {
    data class Success(val book: Book, val rating: Double) : BookApiState
    data object Loading : BookApiState
    data object Error : BookApiState
}

/**
 * ViewModel class for BookDetails page
 *
 * @property key the key value to retrieve the book details from the API
 * */
class BookDetailsVM(private val booksRepository: BookRepository, private val key: String) : ViewModel() {
    var bookApiState: BookApiState by mutableStateOf(BookApiState.Loading)
        private set

    private val _bookDetailsUiState = MutableStateFlow(BookDetailsUiState())
    val bookDetailsUiState = _bookDetailsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            bookApiState = try {
                val bookRequest = async { booksRepository.getBook(key) }
                val ratingsRequest = async { booksRepository.getRatings(key) }
                val book = bookRequest.await()
                val ratings = ratingsRequest.await()
                _bookDetailsUiState.update { it.copy(book = book, ratings = ratings) }
                BookApiState.Success(book, ratings)
            } catch (ex: Exception) {
                BookApiState.Error
            }
        }
    }

    companion object {
        class Factory(private val key: String) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                return modelClass.getConstructor(BookRepository::class.java, String::class.java).newInstance(booksRepository, key)
            }
        }
    }
}
