package com.example.books.fake.dao

import com.example.books.fake.FakeDataSource
import com.example.books.persistence.BookListLineDao
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

class FakeBookListLineDao : BookListLineDao {
    override suspend fun insertBook(book: BookEntity) {
        val list = FakeDataSource.bookEntities.toMutableList()
        list.add(book)
        FakeDataSource.bookEntities = list
    }

    override suspend fun insertBookListLine(bookListLine: BookListLine) {
        val list = FakeDataSource.bookListLines.toMutableList()
        list.add(bookListLine)
        FakeDataSource.bookListLines = list
    }

    override suspend fun deleteBookList(bookListLine: BookListLine) {
        val list = FakeDataSource.bookListLines.toMutableList()
        list.removeIf { it.bookListId == bookListLine.bookListId && it.bookKey == bookListLine.bookKey }
        FakeDataSource.bookListLines = list
    }
}
