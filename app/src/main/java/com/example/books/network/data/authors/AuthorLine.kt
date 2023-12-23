package com.example.books.network.data.authors

import kotlinx.serialization.Serializable

@Serializable
data class AuthorLine(
    val author: AuthorItem = AuthorItem(),
)

@Serializable
data class AuthorItem(
    val key: String = "",
)
