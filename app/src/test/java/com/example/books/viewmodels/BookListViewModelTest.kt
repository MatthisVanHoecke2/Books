package com.example.books.viewmodels

import com.example.books.fake.FakeDataSource
import com.example.books.fake.TestDispatcherRule
import com.example.books.fake.dao.FakeBookDao
import com.example.books.fake.dao.FakeBookListDao
import com.example.books.fake.dao.FakeBookListLineDao
import com.example.books.persistence.BooksDatabase
import com.example.books.repository.NetworkBookListRepository
import com.example.books.ui.screens.booklists.model.BookListGetDBState
import com.example.books.ui.screens.booklists.model.BookListViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class BookListViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private val database = Mockito.mock(BooksDatabase::class.java)

    private val bookListsRepository = NetworkBookListRepository(
        database = database,
    )

    @Before
    fun setup() {
        Mockito.`when`(database.bookDao()).thenReturn(FakeBookDao())
        Mockito.`when`(database.bookListDao()).thenReturn(FakeBookListDao())
        Mockito.`when`(database.bookListLineDao()).thenReturn(FakeBookListLineDao())
    }

    @Test
    fun bookListDetailsViewModel_getLists_verifyBookListGetDBState() = runTest {
        val viewModel = BookListViewModel(bookListsRepository)
        val bookLists = FakeDataSource.bookLists
        val uiStateBookList = viewModel.bookListUiState.value.bookLists
        assertEquals(bookLists, uiStateBookList)
        assertEquals(BookListGetDBState.Success, viewModel.bookListDBState)
    }

    @Test
    fun bookListDetailsViewModel_onTextInput_validateBookListUiStateCreateDialogText() = runTest {
        val viewModel = BookListViewModel(bookListsRepository)
        val testString = "test"
        viewModel.onTextInput(testString)
        assertEquals(testString, viewModel.bookListUiState.value.createDialogText)
    }

    @Test
    fun bookListDetailsViewModel_onOpenCreateDialog_validateBookListUiStateOpenCreateDialog() = runTest {
        val viewModel = BookListViewModel(bookListsRepository)
        val testBoolean = true
        viewModel.onOpenCreateDialog(testBoolean)
        assertEquals(testBoolean, viewModel.bookListUiState.value.openCreateDialog)
    }

    @Test
    fun bookListDetailsViewModel_createList_validateBookListUiStateBookLists() = runTest {
        val viewModel = BookListViewModel(bookListsRepository)
        val listName = "testListA"
        viewModel.createList(listName)
        assertTrue(viewModel.bookListUiState.value.bookLists.any { it.listName == listName })
    }

    @Test
    fun bookListDetailsViewModel_deleteList_validateBookListUiStateBookLists() = runTest {
        val viewModel = BookListViewModel(bookListsRepository)
        val bookLists = FakeDataSource.bookLists.toMutableList()
        val deleteBook = bookLists.first()
        bookLists.remove(deleteBook)
        viewModel.deleteList(deleteBook)
        assertEquals(bookLists, viewModel.bookListUiState.value.bookLists)
    }

    @Test
    fun bookListDetailsViewModel_updateList_validateBookListUiStateBookLists() = runTest {
        val viewModel = BookListViewModel(bookListsRepository)
        val bookList = FakeDataSource.bookLists.first()
        val bookLists = FakeDataSource.bookLists.toMutableList()
        bookLists.remove(bookList)
        viewModel.deleteList(bookList)
        assertEquals(bookLists, viewModel.bookListUiState.value.bookLists)
    }
}
