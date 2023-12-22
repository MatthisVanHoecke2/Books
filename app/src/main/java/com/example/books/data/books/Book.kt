package com.example.books.data.books

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
data class BookDetail(
    @SerialName(value = "key")
    val key: String,
    val covers: List<Long> = emptyList(),
    val title: String,
    @SerialName(value = "authors")
    val authors: List<AuthorLine> = emptyList(),
    @SerialName(value = "first_publish_year")
    val publishYear: Int? = null,
)
