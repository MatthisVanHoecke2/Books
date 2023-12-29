package com.example.books.ui.screens.booklistdetails.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.books.BooksApplication
import com.example.books.model.Book
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.repository.BookListsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookListGetApiState {
    data object Loading : BookListGetApiState
    data class Success(val bookList: List<Book>) : BookListGetApiState
    data class Error(val message: String) : BookListGetApiState
}

class BookListDetailsViewModel(private val bookListRepository: BookListsRepository, private val id: Long) : ViewModel() {
    var bookListApiState: BookListGetApiState by mutableStateOf(BookListGetApiState.Loading)
        private set

    private val _bookListUiState = MutableStateFlow(BookListDetailsUiState())

    init {
        viewModelScope.launch {
            bookListApiState = try {
                val list = bookListRepository.getBookListById(id)
                _bookListUiState.update { it.copy(bookList = list) }
                BookListGetApiState.Success(list)
            } catch (ex: IOException) {
                BookListGetApiState.Error("An error occurred while fetching the list")
            }
        }
    }

    fun deleteBook(bookKey: String) {
        viewModelScope.launch {
            bookListApiState = try {
                bookListRepository.deleteBookFromList(BookListLine(id, bookKey))
                val list = _bookListUiState.value.bookList.toMutableList()
                list.removeIf { it.key == bookKey }
                _bookListUiState.update { it.copy(bookList = list) }
                BookListGetApiState.Success(list)
            } catch (ex: IOException) {
                BookListGetApiState.Error("An error occurred while trying to delete a book from the list")
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
