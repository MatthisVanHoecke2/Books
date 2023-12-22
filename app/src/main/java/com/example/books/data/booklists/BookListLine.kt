package com.example.books.data.booklists

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["booklist_id", "book_key"])
data class BookListLine(
    @ColumnInfo(name = "booklist_id")
    val bookListId: Long,
    @ColumnInfo(name = "book_key")
    val bookKey: String,
)
