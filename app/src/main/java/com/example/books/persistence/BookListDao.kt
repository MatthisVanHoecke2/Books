package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.books.BookEntity

/**
 * DAO for handling book list CRUD operations to the Room database
 * */
@Dao
interface BookListDao {
    /**
     * Inserts a new book list entity into the database
     * @param bookList entity instance of book list
     * @return the generated book list id
     * */
    @Insert
    suspend fun insert(bookList: BookList): Long

    /**
     * Deletes a book list from the database
     * @param bookList entity instance of book list
     * */
    @Delete
    suspend fun delete(bookList: BookList)

    /**
     * Updates a book list
     * @param bookList entity instance of book list
     * */
    @Update
    suspend fun update(bookList: BookList)

    /**
     * Returns all the book lists
     * @return a list of book lists
     * */
    @Query("SELECT * FROM BookList")
    suspend fun getAll(): List<BookList>

    /**
     * Returns a list of books inside a specific book list
     * @return the book list contents
     * */
    @Query("SELECT b.* FROM book b JOIN booklistline bll ON b.book_key = bll.book_key JOIN booklist bl ON bll.booklist_id = bl.booklist_id WHERE bl.booklist_id = :id")
    suspend fun getAllBooksFromList(id: Long): List<BookEntity>

    @Query("SELECT * FROM booklist WHERE booklist_id = :id")
    suspend fun getBookListById(id: Long): BookList
}
