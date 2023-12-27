package com.example.books.repository

import com.example.books.persistence.BooksDatabase
import com.example.books.persistence.data.booklists.BookList

interface BookListsRepository {
    suspend fun getLists(): List<BookList>

    suspend fun createList(name: String): Long

    suspend fun deleteList(bookList: BookList)

    suspend fun updateList(bookList: BookList)
}

class NetworkBookListRepository(private val database: BooksDatabase) : BookListsRepository {
    override suspend fun getLists(): List<BookList> {
        return database.bookListDao().getAll()
    }

    override suspend fun createList(name: String): Long {
        return database.bookListDao().insert(BookList(listName = name))
    }

    override suspend fun deleteList(bookList: BookList) {
        database.bookListDao().delete(bookList)
    }

    override suspend fun updateList(bookList: BookList) {
        database.bookListDao().update(bookList)
    }
}
