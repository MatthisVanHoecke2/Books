package com.example.books.ui.screens.booklistdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.example.books.model.Book

fun LazyListScope.successScreen(searchResult: List<Book>, onNavigate: (String) -> Unit, onDelete: (Book) -> Unit) {
    itemsIndexed(
        searchResult,
    ) { index, book ->
        ListItem(
            leadingContent = {
                if (book.coverId != null) {
                    val imageUrl = "https://covers.openlibrary.org/b/id/${book.coverId}-M.jpg"

                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = book.title,
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        },
                        contentScale = ContentScale.FillWidth,
                    )
                }
            },
            trailingContent = {
                IconButton(
                    onClick = { onDelete.invoke(book) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                    ),
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Delete item")
                }
            },
            headlineContent = { Text(book.title) },
            modifier = Modifier.clickable {
                val key = book.key
                onNavigate.invoke(key)
            },
        )
        if (index < searchResult.lastIndex) {
            Divider(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
        }
    }
}
