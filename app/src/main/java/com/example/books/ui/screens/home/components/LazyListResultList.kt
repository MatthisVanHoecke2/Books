package com.example.books.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book

fun LazyGridScope.resultList(searchResult: List<Book>, onNavigate: (String) -> Unit) {
    items(
        searchResult,
    ) { book ->
        Row(
            modifier = Modifier.clickable {
                val key = book.key
                onNavigate.invoke(key)
            }.padding(dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center,
        ) {
            if (book.coverId != null) {
                val imageUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-L.jpg"

                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = book.title,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Box {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    },
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                Text(book.title)
            }
        }
    }
}
