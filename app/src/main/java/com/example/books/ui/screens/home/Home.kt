package com.example.books.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen() {
    SearchBook()
}

@Composable
fun SearchBook() {
    val viewModel = viewModel<HomeViewModel>()
    val search = viewModel.search.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val loading by viewModel.loading.collectAsState()
    var currentPage by remember { mutableIntStateOf(0) }
    val imagesLoad by remember { mutableStateOf(mutableMapOf<Long, Boolean>()) }

    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
        ) {
            item(span = { GridItemSpan(3) }) {
                Row {
                    TextField(value = search.value, onValueChange = { viewModel.onSearch(it) })
                    Button(enabled = !loading, onClick = { viewModel.searchApi() }) {
                        Text(text = "Search")
                    }
                }
            }
            items(
                searchResult,
            ) { book ->
                if (book.coverId != null) {
                    val imageUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-L.jpg"

                    AsyncImage(
                        model = imageUrl,
                        onLoading = { imagesLoad[book.coverId] = true },
                        onSuccess = { imagesLoad[book.coverId] = false },
                        contentDescription = book.title,
                        modifier = Modifier.clickable {
                        },
                    )
                }
            }
            item(span = { GridItemSpan(3) }) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {
                    if (searchResult.isNotEmpty() && (loading || imagesLoad.containsValue(true))) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    } else if (searchResult.isNotEmpty()) {
                        Button(enabled = !loading, onClick = {
                            currentPage += 1
                            viewModel.expandSearch(offset = (currentPage * 25L))
                        }) {
                            Text("Load more")
                        }
                    }
                }
            }
        }
    }
}
