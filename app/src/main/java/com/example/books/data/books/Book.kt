package com.example.books.data.books

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.books.data.authors.AuthorLine
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookIndex(
    val key: String,
    val title: String,
    @SerialName(value = "cover_i")
    val coverId: Long? = null,
)

@Serializable
@Entity
data class BookDetail(
    @SerialName(value = "key")
    @PrimaryKey
    val bookKey: String,
    @Ignore val covers: List<Long> = emptyList(),
    val title: String,
    @SerialName(value = "authors")
    val authors: List<AuthorLine> = emptyList(),
    @SerialName(value = "first_publish_year")
    val publishYear: Int? = null,
)
