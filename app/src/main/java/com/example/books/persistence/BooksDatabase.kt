package com.example.books.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.data.booklists.BookList
import com.example.books.data.booklists.BookListLine

@Database(
    entities = [
        BookList::class,
        BookListLine::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
    abstract fun bookListDao(): BookListDao
    abstract fun bookDao(): BookDao
}
