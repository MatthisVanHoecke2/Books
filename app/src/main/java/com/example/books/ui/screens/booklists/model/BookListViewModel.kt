package com.example.books.ui.screens.booklists.model

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.books.BooksApplication
import com.example.books.persistence.data.booklists.BookList
import com.example.books.repository.BookListsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookListDBState {
    data object Loading : BookListDBState
    data object Success : BookListDBState
    data object Error : BookListDBState
}

sealed interface BookListModalDBState {
    data object Start : BookListModalDBState
    data object Loading : BookListModalDBState
    data object Success : BookListModalDBState
    data class Error(val message: String) : BookListModalDBState
}

class BookListViewModel(private val bookListsRepository: BookListsRepository) : ViewModel() {
    var bookListDBState: BookListDBState by mutableStateOf(BookListDBState.Loading)
        private set

    var bookListModalDBState: BookListModalDBState by mutableStateOf(BookListModalDBState.Start)
        private set

    private val _bookListUiState = MutableStateFlow(BookListUiState())
    val bookListUiState = _bookListUiState.asStateFlow()

    init {
        viewModelScope.launch {
            bookListDBState = try {
                val lists = bookListsRepository.getLists()
                _bookListUiState.update { it.copy(bookLists = lists) }
                BookListDBState.Success
            } catch (ex: IOException) {
                BookListDBState.Error
            }
        }
    }

    fun onTextInput(value: String) {
        _bookListUiState.update { it.copy(createDialogText = value) }
    }

    fun openCreateDialog(value: Boolean) {
        bookListModalDBState = BookListModalDBState.Start
        _bookListUiState.update { it.copy(openCreateDialog = value, createDialogText = "") }
    }

    fun createList(listName: String) {
        bookListModalDBState = BookListModalDBState.Loading
        viewModelScope.launch {
            bookListModalDBState = try {
                val id: Long = bookListsRepository.createList(listName)
                val bookLists = bookListUiState.value.bookLists.toMutableList()
                bookLists.add(BookList(id, listName))
                _bookListUiState.update { it.copy(openCreateDialog = false, bookLists = bookLists, createDialogText = "") }
                BookListModalDBState.Success
            } catch (ex: IOException) {
                BookListModalDBState.Error("An error occurred while creating a new book list")
            } catch (ex: SQLiteConstraintException) {
                BookListModalDBState.Error("The list '$listName' already exists")
            }
        }
    }

    fun deleteList(bookList: BookList) {
        bookListModalDBState = BookListModalDBState.Loading
        viewModelScope.launch {
            bookListModalDBState = try {
                bookListsRepository.deleteList(bookList)
                val bookLists = bookListUiState.value.bookLists.toMutableList()
                bookLists.remove(bookList)
                _bookListUiState.update { it.copy(openCreateDialog = false, bookLists = bookLists) }
                BookListModalDBState.Success
            } catch (ex: Exception) {
                BookListModalDBState.Error("An error occurred while trying to delete '$bookList'")
            }
        }
    }

    fun updateList(bookList: BookList) {
        bookListModalDBState = BookListModalDBState.Loading

        viewModelScope.launch {
            bookListModalDBState = try {
                bookListsRepository.updateList(bookList)
                val bookLists = bookListUiState.value.bookLists.toMutableList()
                bookLists.removeIf { it.bookListId == bookList.bookListId }
                bookLists.add(bookList)
                _bookListUiState.update { it.copy(openCreateDialog = false, bookLists = bookLists) }
                BookListModalDBState.Success
            } catch (ex: Exception) {
                BookListModalDBState.Error("An error occurred while trying to update '$bookList'")
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
                val bookListsRepository = application.container.bookListsRepository
                BookListViewModel(bookListsRepository)
            }
        }
    }
}
