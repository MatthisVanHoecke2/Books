package com.example.books.repositories

import com.example.books.fake.FakeDataSource
import com.example.books.fake.dao.FakeBookDao
import com.example.books.fake.dao.FakeBookListDao
import com.example.books.fake.dao.FakeBookListLineDao
import com.example.books.persistence.BooksDatabase
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.repository.NetworkBookListRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NetworkBookListsRepositoryTest {
    private val database = Mockito.mock(BooksDatabase::class.java)

    private val booksRepository = NetworkBookListRepository(
        database = database,
    )

    @Before
    fun setup() {
        Mockito.`when`(database.bookDao()).thenReturn(FakeBookDao())
        Mockito.`when`(database.bookListDao()).thenReturn(FakeBookListDao())
        Mockito.`when`(database.bookListLineDao()).thenReturn(FakeBookListLineDao())
    }

    @Test
    fun networkBooksRepository_getLists_verifyBookLists() = runTest {
        Assert.assertEquals(FakeDataSource.bookLists, booksRepository.getLists())
    }

    @Test
    fun networkBooksRepository_getBookListById_verifyBookList() = runTest {
        val id = 1L
        Assert.assertEquals(FakeDataSource.bookLists.first { it.bookListId == id }, booksRepository.getBookListById(id))
    }

    @Test
    fun networkBooksRepository_getListOfBooksById_verifyListOfBooks() = runTest {
        val id = 1L
        val fakeDataList = FakeDataSource.bookListLines.filter { it.bookListId == id }.map { line -> FakeDataSource.bookEntities.first { it.key == line.bookKey } }
        Assert.assertEquals(fakeDataList, booksRepository.getListOfBooksById(id))
    }

    @Test
    fun networkBooksRepository_createList_verifyBookLists() = runTest {
        val fakeDataList = FakeDataSource.bookLists.toMutableList()
        val name = "test 3"
        val id = fakeDataList.last().bookListId + 1
        fakeDataList.add(BookList(id, listName = name))
        booksRepository.createList(name)
        Assert.assertEquals(fakeDataList, booksRepository.getLists())
    }

    @Test
    fun networkBooksRepository_deleteList_verifyBookLists() = runTest {
        val fakeDataList = FakeDataSource.bookLists.toMutableList()
        val idToRemove = 2L
        val bookListToRemove = fakeDataList.first { it.bookListId == idToRemove }
        fakeDataList.remove(bookListToRemove)
        booksRepository.deleteList(bookListToRemove)
        Assert.assertEquals(fakeDataList, booksRepository.getLists())
    }

    @Test
    fun networkBooksRepository_deleteBookFromList_verifyListOfBooks() = runTest {
        val bookListId = 1L
        val fakeDataList = FakeDataSource.bookListLines.filter { it.bookListId == bookListId }.toMutableList()
        val keyToRemove = "OL1568131W"
        val bookListLineToRemove = fakeDataList.first { it.bookKey == keyToRemove }
        fakeDataList.remove(bookListLineToRemove)
        booksRepository.deleteBookFromList(bookListLineToRemove)
        Assert.assertEquals(fakeDataList.map { line -> FakeDataSource.bookEntities.first { it.key == line.bookKey } }, booksRepository.getListOfBooksById(bookListId))
    }

    @Test
    fun networkBooksRepository_insertIntoList_verifyListOfBooks() = runTest {
        val book = FakeDataSource.bookEntities.first()
        val bookList = FakeDataSource.bookLists.last()
        val fakeDataList = FakeDataSource.bookListLines.filter { it.bookListId == bookList.bookListId }.toMutableList()
        fakeDataList.add(BookListLine(bookList.bookListId, book.key))
        booksRepository.insertIntoList(bookList, book)
        Assert.assertEquals(fakeDataList.map { line -> FakeDataSource.bookEntities.first { it.key == line.bookKey } }, booksRepository.getListOfBooksById(bookList.bookListId))
    }

    @Test
    fun networkBooksRepository_updateList_verifyBookList() = runTest {
        val newName = "testA"
        val bookList = FakeDataSource.bookLists.first().copy(listName = newName)
        booksRepository.updateList(bookList)
        Assert.assertEquals(bookList, booksRepository.getBookListById(bookList.bookListId))
    }
}
