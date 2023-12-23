package com.example.books.persistence

import androidx.room.Dao
import androidx.room.Insert
import com.example.books.persistence.data.authors.AuthorBook

@Dao
interface AuthorLineDao {
    @Insert
    fun insert(authorBook: AuthorBook)
}
