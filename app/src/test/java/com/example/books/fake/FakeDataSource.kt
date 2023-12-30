package com.example.books.fake

import com.example.books.network.data.books.BookDetail
import com.example.books.network.data.books.BookIndex
import com.example.books.network.data.books.Rating
import com.example.books.network.data.books.Summary
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

object FakeDataSource {
    var bookEntities = listOf(
        BookEntity(
            key = "OL1568131W",
            title = "testbook 1",
            coverId = null,
            rating = 4.5,
        ),
        BookEntity(
            key = "OL1568132W",
            title = "testbook 2",
            coverId = null,
            rating = 4.8,
        ),
    )
    val bookIndices = listOf(
        BookIndex(
            key = "OL1568131W",
            title = "testbook 1",
            coverId = null,
        ),
        BookIndex(
            key = "OL1568132W",
            title = "testbook 2",
            coverId = null,
        ),
    )
    val bookDetails = listOf(
        BookDetail(
            key = "OL1568131W",
            title = "testbook 1",
            coverId = null,
        ),
        BookDetail(
            key = "OL1568132W",
            title = "testbook 2",
            coverId = null,
        ),
    )
    val ratings = listOf(
        "OL1568131W" to Rating(Summary(average = 4.5, count = 150)),
        "OL1568132W" to Rating(Summary(average = 4.8, count = 224)),
    )
    var bookLists = listOf(
        BookList(
            1,
            "testlist 1",
        ),
        BookList(
            2,
            "testlist 2",
        ),
    )
    var bookListLines = listOf(
        BookListLine(
            1,
            "OL1568131W",
        ),
        BookListLine(
            1,
            "OL1568132W",
        ),
        BookListLine(
            2,
            "OL1568132W",
        ),
    )
}
