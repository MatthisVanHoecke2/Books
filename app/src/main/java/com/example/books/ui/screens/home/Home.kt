package com.example.books.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen() {
    SearchBook()
}

@Composable
fun SearchBook() {
    val viewModel = viewModel<HomeViewModel>()
    val search = viewModel.search.collectAsState()

    Column {
        TextField(value = search.value, onValueChange = { viewModel.onSearch(it) })
        Button(onClick = { viewModel.onClick() }) {
            Text(text = "Click")
        }
    }
}
