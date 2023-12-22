package com.example.books.data.books

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class BookIndex(
    val key: String,
    val title: String,
    @SerialName(value = "cover_i")
    val coverId: Long? = null,
)

@Serializable
data class BookDetail(
    val key: String,
    val covers: List<Long> = emptyList(),
    val title: String,
    val description: JsonElement? = null,
    @SerialName(value = "authors")
    val authors: List<Author> = emptyList(),
    @SerialName(value = "first_publish_year")
    val publishYear: Int? = null,
)

@Serializable
data class Author(
    val author: AuthorItem,
    val type: AuthorItem,
)

@Serializable
data class AuthorItem(
    val key: String,
)
