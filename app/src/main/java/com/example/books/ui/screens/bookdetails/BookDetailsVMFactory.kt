package com.example.books.ui.screens.bookdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookDetailsVMFactory(private val key: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java).newInstance(key)
    }
}
