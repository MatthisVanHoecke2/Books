package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

/**
 * DAO for handling book list - book associate entity CRUD operations to the Room database
 * */
@Dao
interface BookListLineDao {
    /**
     * Inserts a new book entity into the database
     * @param book entity instance of book
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookEntity)

    /**
     * Inserts an associate entity between the book and book list columns
     * @param bookListLine entity instance of associate book list line
     * */
    @Insert
    suspend fun insertBookListLine(bookListLine: BookListLine)

    /**
     * Deletes an associate entity
     * @param bookListLine entity instance of associate book list line
     * */
    @Delete
    suspend fun deleteBookList(bookListLine: BookListLine)

    /**
     * Transaction for adding a new book list line entity
     * If the book does not exist in the Room database yet, it will be inserted before inserting the book list line entity
     * @param bookList book list entity
     * @param book book entity
     * */
    @Transaction
    suspend fun insertWithListLine(bookList: BookList, book: BookEntity) {
        insertBook(book.copy(key = book.key.replace("/works/", "")))
        insertBookListLine(BookListLine(bookList.bookListId, book.key.replace("/works/", "")))
    }
}
