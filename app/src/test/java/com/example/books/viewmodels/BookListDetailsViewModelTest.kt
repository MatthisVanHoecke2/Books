package com.example.books.viewmodels

import com.example.books.fake.FakeDataSource
import com.example.books.fake.TestDispatcherRule
import com.example.books.fake.dao.FakeBookDao
import com.example.books.fake.dao.FakeBookListDao
import com.example.books.fake.dao.FakeBookListLineDao
import com.example.books.persistence.BooksDatabase
import com.example.books.repository.NetworkBookListRepository
import com.example.books.ui.screens.booklistdetails.model.BookListApiState
import com.example.books.ui.screens.booklistdetails.model.BookListDetailsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class BookListDetailsViewModelTest {
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
    fun homeViewModel_getBookList_verifyBookListApiState() = runTest {
        val id = 1L
        val viewModel = BookListDetailsViewModel(bookListsRepository, id)
        val bookList = FakeDataSource.bookLists.first { it.bookListId == id }
        val listOfBooks = FakeDataSource.bookListLines.filter { it.bookListId == id }.map { line -> FakeDataSource.bookEntities.first { it.key == line.bookKey } }
        Assert.assertEquals(BookListApiState.Success(bookList, listOfBooks), viewModel.bookListApiState)
    }

    @Test
    fun homeViewModel_deleteBook_verifyBookListApiState() = runTest {
        val keyToRemove = "OL1568131W"
        val id = 1L
        val viewModel = BookListDetailsViewModel(bookListsRepository, id)
        val bookList = FakeDataSource.bookLists.first { it.bookListId == id }
        val listOfBooks = FakeDataSource.bookListLines.filter { it.bookListId == id }.map { line -> FakeDataSource.bookEntities.first { it.key == line.bookKey } }.toMutableList()
        listOfBooks.removeIf { it.key == keyToRemove }
        viewModel.deleteBook(keyToRemove)
        Assert.assertEquals(BookListApiState.Success(bookList, listOfBooks), viewModel.bookListApiState)
    }
}
