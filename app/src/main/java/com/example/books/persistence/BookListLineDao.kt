package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.example.books.persistence.data.booklists.BookList
import com.example.books.persistence.data.booklists.BookListLine
import com.example.books.persistence.data.books.BookEntity

@Dao
interface BookListLineDao : BookDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBookListLine(bookListLine: BookListLine)

    @Delete
    suspend fun deleteBookList(bookListLine: BookListLine)

    @Transaction
    suspend fun insertWithListLine(bookList: BookList, book: BookEntity) {
        insert(book.copy(key = book.key.replace("/works/", "")))
        insertBookListLine(BookListLine(bookList.bookListId, book.key.replace("/works/", "")))
    }
}
