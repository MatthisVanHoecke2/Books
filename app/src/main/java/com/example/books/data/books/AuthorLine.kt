package com.example.books.data.books

import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.serialization.Serializable

@Serializable
@Entity(primaryKeys = ["authorKey", "bookKey"])
data class AuthorLine(
    val authorKey: String = "",
    val bookKey: String = "",
    @Ignore val author: AuthorItem,
)

@Serializable
data class AuthorItem(
    val key: String,
)
