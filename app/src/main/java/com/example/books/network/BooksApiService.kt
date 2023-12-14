package com.example.books.network

import com.example.books.data.books.Book
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = createRetrofit("https://openlibrary.org")

interface BooksApiService {
    @GET("/search.json")
    suspend fun getBooks(@Query("q") query: String): List<Book>
}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}
