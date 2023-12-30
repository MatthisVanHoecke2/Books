package com.example.books.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.books.R
import com.example.books.model.Book

/**
 * Screen to display a loading spinner when the book lists are being retrieved
 * @param loadedList already loaded books, in case the request was made to expand the current list
 * @param onNavigate callback for navigating to a book details page
 * */
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
