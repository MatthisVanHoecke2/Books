package com.example.books.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

@Database(
    entities = [
        BookList::class,
        BookListLine::class,
        BookEntity::class,
    ],
    version = 1,
)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun bookListDao(): BookListDao
    abstract fun bookListLineDao(): BookListLineDao
    abstract fun bookDao(): BookDao
}
