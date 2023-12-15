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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.books.R
import com.example.books.ui.shared.CustomTextField

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
    var imagesLoad by remember { mutableStateOf(emptyMap<Long, Boolean>()) }

    fun search() {
        val newMap = imagesLoad.toMutableMap()
        newMap.clear()
        imagesLoad = newMap
        viewModel.searchApi()
    }

    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
        ) {
            item(span = { GridItemSpan(3) }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomTextField(value = search.value, onValueChange = { viewModel.onSearch(it) }, onDone = { search() }, onClear = { viewModel.onSearch("") })
                    Button(enabled = !loading, onClick = { search() }) {
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
                        onLoading = { imagesLoad = imagesLoad.toMutableMap().apply { put(book.coverId, true) } },
                        onSuccess = { imagesLoad = imagesLoad.toMutableMap().apply { put(book.coverId, false) } },
                        contentDescription = book.title,
                        modifier = Modifier.clickable {
                        },
                    )
                }
            }
            item(span = { GridItemSpan(3) }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                ) {
                    if (searchResult.isNotEmpty() && (loading || imagesLoad.containsValue(true))) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    } else if (searchResult.isNotEmpty()) {
                        Button(onClick = {
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
