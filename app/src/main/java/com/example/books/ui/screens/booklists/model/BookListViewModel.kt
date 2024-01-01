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

/**
 * Interface for determining the current state of a book list GET operation
 * */
sealed interface BookListGetDBState {
    /**
     * Data object for when the operation is loading
     * */
    data object Loading : BookListGetDBState

    /**
     * Data object for when the operation was successfully executed
     * */
    data object Success : BookListGetDBState

    /**
     * Data class for when the operation has failed
     * @param message error message to display
     * */
    data class Error(val message: String) : BookListGetDBState
}

/**
 * Interface for determining the current state of a book list CREATE or UPDATE operation
 * */
sealed interface BookListCreateUpdateDBState {
    /**
     * Data object for when the operation is waiting to be executed
     * */
    data object Start : BookListCreateUpdateDBState

    /**
     * Data object for when the operation is loading
     * */
    data object Loading : BookListCreateUpdateDBState

    /**
     * Data object for when the operation was successfully executed
     * */
    data object Success : BookListCreateUpdateDBState

    /**
     * Data class for when the operation has failed
     * @param message error message to display
     * */
    data class Error(val message: String) : BookListCreateUpdateDBState
}

/**
 * ViewModel class for BookList page
 * @property bookListsRepository [BookListsRepository] instance for handling book list CRUD operations
 * */
class BookListViewModel(private val bookListsRepository: BookListsRepository) : ViewModel() {
    /**
     * Variable for holding the current [BookListGetDBState]
     * */
    var bookListDBState: BookListGetDBState by mutableStateOf(BookListGetDBState.Loading)
        private set

    /**
     * Value for holding the current [BookListCreateUpdateDBState]
     * */
    var bookListModalDBState: BookListCreateUpdateDBState by mutableStateOf(BookListCreateUpdateDBState.Start)
        private set

    private val _bookListUiState = MutableStateFlow(BookListUiState())

    /**
     * Value for holding the current [BookListUiState]
     * */
    val bookListUiState = _bookListUiState.asStateFlow()

    /**
     * Function to be executed on initialization, loads all the necessary data into the ViewModel
     * */
    init {
        viewModelScope.launch {
            bookListDBState = try {
                val lists = bookListsRepository.getLists()
                _bookListUiState.update { it.copy(bookLists = lists) }
                BookListGetDBState.Success
            } catch (ex: IOException) {
                BookListGetDBState.Error("An error occurred while fetching the book lists")
            }
        }
    }

    /**
     * Changes the create dialog textfield value
     * @param value new value to update the textfield value with
     * */
    fun onTextInput(value: String) {
        _bookListUiState.update { it.copy(createDialogText = value) }
    }

    /**
     * Opens or closes the create dialog
     * @param value value to determine whether dialog should be opened
     * */
    fun onOpenCreateDialog(value: Boolean) {
        bookListModalDBState = BookListCreateUpdateDBState.Start
        _bookListUiState.update { it.copy(openCreateDialog = value, createDialogText = "") }
    }

    /**
     * Creates a new [BookList]
     * @param listName name of the new list
     * */
    fun createList(listName: String) {
        bookListModalDBState = BookListCreateUpdateDBState.Loading
        viewModelScope.launch {
            bookListModalDBState = try {
                val id: Long = bookListsRepository.createList(listName)
                val bookLists = bookListUiState.value.bookLists.toMutableList()
                bookLists.add(BookList(id, listName))
                _bookListUiState.update { it.copy(openCreateDialog = false, bookLists = bookLists, createDialogText = "") }
                BookListCreateUpdateDBState.Success
            } catch (ex: IOException) {
                BookListCreateUpdateDBState.Error("An error occurred while creating a new book list")
            } catch (ex: SQLiteConstraintException) {
                BookListCreateUpdateDBState.Error("The list '$listName' already exists")
            }
        }
    }

    /**
     * Validates the entered list name
     * @param name new list name
     * @return true if name is valid
     * */
    fun validateName(name: String): Boolean {
        return !(name.isEmpty() || name.isBlank())
    }

    /**
     * Deletes a specific [BookList]
     * @param bookList list to delete
     * */
    fun deleteList(bookList: BookList) {
        bookListModalDBState = BookListCreateUpdateDBState.Loading
        viewModelScope.launch {
            bookListModalDBState = try {
                bookListsRepository.deleteList(bookList)
                val bookLists = bookListUiState.value.bookLists.toMutableList()
                bookLists.remove(bookList)
                _bookListUiState.update { it.copy(openCreateDialog = false, bookLists = bookLists) }
                BookListCreateUpdateDBState.Success
            } catch (ex: Exception) {
                BookListCreateUpdateDBState.Error("An error occurred while trying to delete '$bookList'")
            }
        }
    }

    /**
     * Updates a specific [BookList]
     * @param bookList updated list
     * */
    fun updateList(bookList: BookList) {
        bookListModalDBState = BookListCreateUpdateDBState.Loading

        viewModelScope.launch {
            bookListModalDBState = try {
                bookListsRepository.updateList(bookList)
                val bookLists = bookListUiState.value.bookLists.toMutableList()
                bookLists.removeIf { it.bookListId == bookList.bookListId }
                bookLists.add(bookList)
                _bookListUiState.update { it.copy(openCreateDialog = false, bookLists = bookLists) }
                BookListCreateUpdateDBState.Success
            } catch (ex: Exception) {
                BookListCreateUpdateDBState.Error("An error occurred while trying to update '$bookList'")
            }
        }
    }

    companion object {
        /**
         * Factory object for creating BookListViewModel instance
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
