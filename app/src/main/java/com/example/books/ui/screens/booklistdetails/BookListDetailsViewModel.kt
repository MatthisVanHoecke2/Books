package com.example.books.ui.screens.booklistdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.books.BooksApplication
import com.example.books.model.Book
import com.example.books.repository.BookListsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface BookListApiState {
    data object Loading : BookListApiState
    data class Success(val bookList: List<Book>) : BookListApiState
    data object Error : BookListApiState
}

class BookListDetailsViewModel(private val bookListRepository: BookListsRepository, private val id: Long) : ViewModel() {
    var bookListApiState: BookListApiState by mutableStateOf(BookListApiState.Loading)
        private set

    private val _bookListUiState = MutableStateFlow(BookListDetailsUiState())
    val bookListUiState = _bookListUiState.asStateFlow()

    init {
        viewModelScope.launch {
            bookListApiState = try {
                val list = bookListRepository.getBookListById(id)
                _bookListUiState.update { it.copy(bookList = list) }
                BookListApiState.Success(list)
            } catch (ex: Exception) {
                BookListApiState.Error
            }
        }
    }

    companion object {
        class Factory(private val id: Long) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val bookListsRepository = application.container.bookListsRepository
                return modelClass.getConstructor(BookListsRepository::class.java, Long::class.java).newInstance(bookListsRepository, id)
            }
        }
    }
}
