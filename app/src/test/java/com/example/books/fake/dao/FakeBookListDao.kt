package com.example.books.fake.dao

import com.example.books.fake.FakeDataSource
import com.example.books.persistence.BookListDao
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.books.BookEntity

class FakeBookListDao : BookListDao {
    override suspend fun insert(bookList: BookList): Long {
        val list = FakeDataSource.bookLists.toMutableList()
        val id = FakeDataSource.bookLists.last().bookListId
        list.add(bookList.copy(bookListId = id + 1))
        FakeDataSource.bookLists = list
        return id
    }

    override suspend fun delete(bookList: BookList) {
        val list = FakeDataSource.bookLists.toMutableList()
        list.removeIf { it.bookListId == bookList.bookListId }
        FakeDataSource.bookLists = list
    }

    override suspend fun update(bookList: BookList) {
        val list = FakeDataSource.bookLists.toMutableList()
        list.removeIf { it.bookListId == bookList.bookListId }
        list.add(bookList)
        FakeDataSource.bookLists = list
    }

    override suspend fun getAll(): List<BookList> {
        return FakeDataSource.bookLists
    }

    override suspend fun getAllBooksFromList(id: Long): List<BookEntity> {
        return FakeDataSource.bookListLines.filter { it.bookListId == id }.map { line -> FakeDataSource.bookEntities.first { it.key == line.bookKey } }
    }

    override suspend fun getBookListById(id: Long): BookList {
        return FakeDataSource.bookLists.first { it.bookListId == id }
    }
}