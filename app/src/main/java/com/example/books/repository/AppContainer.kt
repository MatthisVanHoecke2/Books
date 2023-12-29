package com.example.books.repository

import android.content.Context
import androidx.room.Room
import com.example.books.network.BooksApiService
import com.example.books.network.isInternetAvailable
import com.example.books.persistence.BooksDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Interface for creating a container class for holding variables needed to be accessible everywhere in the application
 * @property booksRepository value for holding the [BookRepository] instance
 * @property bookListsRepository value for holding the [BookListsRepository] instance
 * */
interface AppContainer {
    val booksRepository: BookRepository
    val bookListsRepository: BookListsRepository
}

private val json = Json { ignoreUnknownKeys = true }

/**
 * Real class implementation of [AppContainer]
 * @property context application context
 * */
class DefaultAppContainer(private val context: Context) : AppContainer {
    private val BASE_URL =
        "https://openlibrary.org"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val database = Room.databaseBuilder(
        context,
        BooksDatabase::class.java,
        "books-db",
    ).build()

    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }

    private fun checkConnection(): Boolean {
        return isInternetAvailable(context)
    }

    override val booksRepository: BookRepository by lazy {
        NetworkBookRepository(booksApiService = retrofitService, database, ::checkConnection)
    }

    override val bookListsRepository: BookListsRepository by lazy {
        NetworkBookListRepository(database)
    }
}
