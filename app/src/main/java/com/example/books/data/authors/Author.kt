package com.example.books.data.authors

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Author(
    @SerialName(value = "key")
    @PrimaryKey
    val authorKey: String = "",
    val books: List<AuthorLine> = emptyList(),
    val name: String = "",
)
