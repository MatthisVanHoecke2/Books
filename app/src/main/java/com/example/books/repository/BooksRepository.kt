package com.example.books.repository

import android.content.Context
import androidx.room.Room
import com.example.books.data.books.BookIndex
import com.example.books.data.books.BookResult
import com.example.books.network.BooksApiService
import com.example.books.network.isInternetAvailable
import com.example.books.persistence.BooksDatabase

class BooksRepository(private val context: Context, private val service: BooksApiService) {
    private val db = Room.databaseBuilder(
        context,
        BooksDatabase::class.java,
        "books-db",
    ).build()

    suspend fun getBooks(query: String, offset: Long = 0, limit: Long = 25): BookResult {
        return if (isInternetAvailable(context)) {
            service.getBooks(query, offset, limit)
        } else {
            val found = db.bookDao().getAll().map { BookIndex(key = it.bookKey, coverId = null, title = it.title) }
            BookResult(found.size.toLong(), found)
        }
    }

    fun getBook(key: String) {
    }

    fun getRating(key: String) {
    }
}
