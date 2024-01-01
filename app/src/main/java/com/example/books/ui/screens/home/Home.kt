package com.example.books.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.R
import com.example.books.ui.screens.home.components.errorScreen
import com.example.books.ui.screens.home.components.loadingScreen
import com.example.books.ui.screens.home.components.successScreen
import com.example.books.ui.screens.home.model.BookApiState
import com.example.books.ui.screens.home.model.HomeViewModel
import com.example.books.ui.shared.CustomTextField

/**
 * Composable screen for displaying the Home page
 * @param onNavigate callback function for navigating to a BookDetails page
 * */
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
                    Button(
                        enabled = apiState is BookApiState.Success || apiState is BookApiState.Start,
                        onClick = { viewModel.searchApi() },
                    ) {
                        Text(text = stringResource(R.string.home_button_search))
                    }
                }
            }
            when (apiState) {
                is BookApiState.Loading -> loadingScreen(
                    loadedList = searchResult,
                    onNavigate = { onNavigate.invoke(it) },
                )
                is BookApiState.Success -> successScreen(
                    searchResult = apiState.books,
                    expandSearch = { viewModel.expandSearch() },
                    onNavigate = { onNavigate.invoke(it) },
                    endOfList = homeUiState.endOfList,
                )
                is BookApiState.Error -> errorScreen(apiState.message)
                is BookApiState.Start -> {}
            }
        }
    }
}
