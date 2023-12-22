package com.example.books.data.booklists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookList(
    @PrimaryKey
    @ColumnInfo(name = "booklist_id")
    val bookListId: Long,
    @ColumnInfo(name = "list_name")
    val listName: String,
)
