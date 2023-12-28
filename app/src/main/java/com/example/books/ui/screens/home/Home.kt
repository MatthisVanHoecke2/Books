package com.example.books.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.R
import com.example.books.model.Book
import com.example.books.ui.screens.home.components.resultList
import com.example.books.ui.screens.home.model.BookApiState
import com.example.books.ui.screens.home.model.HomeViewModel
import com.example.books.ui.shared.CustomTextField

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val viewModel = viewModel<HomeViewModel>(factory = HomeViewModel.Factory)
    val homeUiState by viewModel.homeUiState.collectAsState()
    val search = homeUiState.search
    val searchResult = homeUiState.searchResult
    val apiState = viewModel.bookApiState

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
                    Button(enabled = apiState is BookApiState.Success || apiState is BookApiState.Start, onClick = { viewModel.searchApi() }) {
                        Text(text = "Search")
                    }
                }
            }
            when (apiState) {
                is BookApiState.Loading -> loadingScreen(loadedList = searchResult, onNavigate = { onNavigate.invoke(it) })
                is BookApiState.Success -> successScreen(searchResult = apiState.books, expandSearch = { viewModel.expandSearch() }, onNavigate = { onNavigate.invoke(it) }, endOfList = homeUiState.endOfList)
                is BookApiState.Error -> errorScreen()
                is BookApiState.Start -> {}
            }
        }
    }
}

fun LazyGridScope.errorScreen() {
    item(span = { GridItemSpan(maxLineSpan) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {
            Text("An error occurred while fetching data")
        }
    }
}

fun LazyGridScope.loadingScreen(loadedList: List<Book>, onNavigate: (String) -> Unit) {
    resultList(searchResult = loadedList, onNavigate = onNavigate)
    item(span = { GridItemSpan(maxLineSpan) }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

fun LazyGridScope.successScreen(searchResult: List<Book>, expandSearch: () -> Unit, onNavigate: (String) -> Unit, endOfList: Boolean) {
    resultList(searchResult = searchResult, onNavigate = onNavigate)
    if (!endOfList) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            if (searchResult.isEmpty()) {
                Text("No books found")
            } else {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                ) {
                    Button(onClick = {
                        expandSearch.invoke()
                    }) {
                        Text("Load more")
                    }
                }
            }
        }
    }
}
