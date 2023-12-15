package com.example.books.network

import com.example.books.data.books.BookResult
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = createRetrofit("https://openlibrary.org")

interface BooksApiService {
    @GET("/search.json")
    suspend fun getBooks(@Query("q") query: String, @Query("offset") offset: Long = 0, @Query("limit") limit: Long = 25): BookResult
}

object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}
