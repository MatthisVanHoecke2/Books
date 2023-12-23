package com.example.books.persistence.data.authors

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["book_key", "author_key"])
data class AuthorBook(
    @ColumnInfo(name = "book_key")
    val bookKey: String,
    @ColumnInfo(name = "author_key")
    val authorKey: String,
)
