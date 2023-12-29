package com.example.books

import android.app.Application
import com.example.books.repository.AppContainer
import com.example.books.repository.DefaultAppContainer

/**
 * Application class initialized on startup
 * */
class BooksApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}
