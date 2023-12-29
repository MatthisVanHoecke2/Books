package com.example.books.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.books.R
import com.example.books.model.Book

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