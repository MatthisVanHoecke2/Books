package com.example.books.network.data.books

import kotlinx.serialization.Serializable

@Serializable
data class BookResult(val numFound: Long, val docs: List<BookIndex>)