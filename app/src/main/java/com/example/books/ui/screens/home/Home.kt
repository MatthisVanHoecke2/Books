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
fun HomeScreen(onNavigate: (String) -> Unit) {
    SearchBook(onNavigate)
}

@Composable
fun SearchBook(onNavigate: (String) -> Unit) {
    val viewModel = viewModel<HomeViewModel>()
    val homeUiState by viewModel.homeUiState.collectAsState()
    val search = homeUiState.search
    val searchResult = homeUiState.searchResult
    val loading by viewModel.loading.collectAsState()
    var currentPage by remember { mutableIntStateOf(0) }

    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomTextField(
                        value = search,
                        onValueChange = { viewModel.onSearch(it) },
                        onDone = { viewModel.searchApi() },
                        onClear = { viewModel.onSearch("") },
                        modifier = Modifier.weight(1f),
                    )
                    Button(enabled = !loading, onClick = { viewModel.searchApi() }) {
                        Text(text = "Search")
                    }
                }
            }
            items(
                searchResult,
            ) { book ->
                Row(
                    modifier = Modifier.clickable {
                        val key = book.key.replace("/works/", "")
                        onNavigate.invoke(key)
                    },
                    horizontalArrangement = Arrangement.Center,
                ) {
                    if (book.coverId != null) {
                        val imageUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-L.jpg"

                        AsyncImage(
                            model = imageUrl,
                            contentDescription = book.title,
                        )
                    } else {
                        Text(book.title)
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                ) {
                    if (loading) {
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
