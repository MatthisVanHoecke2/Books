package com.example.books.ui.screens.bookdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.books.BooksApplication
import com.example.books.repository.BookRepository
import com.example.books.ui.CustomViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel class for BookDetails page
 *
 * @property key the key value to retrieve the book details from the API
 * */
class BookDetailsVM(private val booksRepository: BookRepository, private val key: String) : CustomViewModel() {
    private val _bookDetailsUiState = MutableStateFlow(BookDetailsUiState())
    val bookDetailsUiState = _bookDetailsUiState.asStateFlow()

    init {
        onLoadChange(true)
        viewModelScope.launch {
            val book = async { booksRepository.getBook(key) }
            val ratings = async { booksRepository.getRatings(key) }
            _bookDetailsUiState.update { it.copy(book = book.await(), ratings = ratings.await()) }
            onLoadChange(false)
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
