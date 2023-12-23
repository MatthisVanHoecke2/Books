package com.example.books.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.persistence.data.authors.AuthorBook
import com.example.books.persistence.data.authors.AuthorEntity
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

@Database(
    entities = [
        AuthorBook::class,
        AuthorEntity::class,
        BookList::class,
        BookListLine::class,
        BookEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
    abstract fun authorLineDao(): AuthorLineDao
    abstract fun bookListDao(): BookListDao
    abstract fun bookListLineDao(): BookListLineDao
    abstract fun bookDao(): BookDao
    companion object {
        private var instance: BooksDatabase? = null

        fun getInstance(): BooksDatabase? { return instance }
    }
}
