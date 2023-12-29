package com.example.books.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

/**
 * Class for configuring the database
 * */
@Database(
    entities = [
        BookList::class,
        BookListLine::class,
        BookEntity::class,
    ],
    version = 1,
)
abstract class BooksDatabase : RoomDatabase() {
    /**
     * Function for retrieving the book list dao
     * @return instance of the book list dao
     * */
    abstract fun bookListDao(): BookListDao

    /**
     * Function for retrieving the book list associate entity dao
     * @return instance of the book list associate entity dao
     * */
    abstract fun bookListLineDao(): BookListLineDao

    /**
     * Function for retrieving the book dao
     * @return instance of the book dao
     * */
    abstract fun bookDao(): BookDao
}
