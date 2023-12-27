package com.example.books.persistence.data.booklists

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["list_name"], unique = true)])
data class BookList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "booklist_id")
    val bookListId: Long = 0,
    @ColumnInfo(name = "list_name")
    val listName: String,
)
