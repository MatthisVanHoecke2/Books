package com.example.books.network.data.books

import kotlinx.serialization.Serializable

/**
 * Result class for retrieving data from the API
 * @property numFound amount of books queried from the API
 * @property docs list of books queried from the API
 * */
@Serializable
data class BookResult(val numFound: Long, val docs: List<BookIndex>)
