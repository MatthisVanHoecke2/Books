package com.example.books.data.authors

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val key: String = "",
    val books: List<AuthorLine> = emptyList(),
    val name: String = "",
)
