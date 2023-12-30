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
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity
import com.example.books.repository.BookListsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Interface for determining the current state of a book list CRUD operation
 * */
sealed interface BookListApiState {
    /**
     * Data object for when the operation is loading
     * */
    data object Loading : BookListApiState

    /**
     * Data class for when the operation was successfully executed
     * @param listOfBooks the list of [Book] that was retrieved from the [BookListsRepository]
     * */
    data class Success(val bookList: BookList, val listOfBooks: List<Book>) : BookListApiState

    /**
     * Data class for when the operation has failed
     * @param message error message to display
     * */
    data class Error(val message: String) : BookListApiState
}

/**
 * ViewModel class for BookListDetails page
 * @property bookListsRepository [BookListsRepository] instance for handling book list CRUD operations
 * @property id the key value to retrieve the book list details from the API
 * */
class BookListDetailsViewModel(private val bookListsRepository: BookListsRepository, private val id: Long) : ViewModel() {
    /**
     * Variable for holding the current [BookListApiState]
     * */
    var bookListApiState: BookListApiState by mutableStateOf(BookListApiState.Loading)
        private set

    private val _bookListUiState = MutableStateFlow(BookListDetailsUiState())

    /**
     * Function to be executed on initialization, loads all the necessary data into the ViewModel
     * */
    init {
        viewModelScope.launch {
            bookListApiState = try {
                val bookList = async { bookListsRepository.getBookListById(id) }.await()
                val list = async { bookListsRepository.getListOfBooksById(id) }.await()
                _bookListUiState.update { it.copy(bookList = bookList, listOfBooks = list) }
                BookListApiState.Success(bookList, list)
            } catch (ex: IOException) {
                BookListApiState.Error("An error occurred while fetching the list")
            }
        }
    }

    /**
     * Deletes a [BookEntity] from the current book list
     * @param bookKey primary key of the book to be inserted
     * */
    fun deleteBook(bookKey: String) {
        viewModelScope.launch {
            bookListApiState = try {
                bookListsRepository.deleteBookFromList(BookListLine(id, bookKey))
                val bookList = _bookListUiState.value.bookList
                val list = _bookListUiState.value.listOfBooks.toMutableList()
                list.removeIf { it.key == bookKey }
                _bookListUiState.update { it.copy(listOfBooks = list) }
                BookListApiState.Success(bookList!!, list)
            } catch (ex: IOException) {
                BookListApiState.Error("An error occurred while trying to delete a book from the list")
            }
        }
    }

    companion object {
        /**
         * Custom ViewModel factory to pass variables to the [BookListDetailsViewModel]
         * @param id selected book list identifier
         * */
        class Factory(private val id: Long) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = (extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val bookListsRepository = application.container.bookListsRepository
                return modelClass.getConstructor(BookListsRepository::class.java, Long::class.java).newInstance(bookListsRepository, id)
            }
        }
    }
}
