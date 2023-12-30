package com.example.books.ui.screens.booklistdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.shared.DetailComponent

/**
 * Screen to display when the [BookList] has been retrieved successfully
 * @param bookListItems list of books inside the book list
 * @param onNavigate callback function for navigating to a book detail page
 * @param onDelete callback function for removing a [Book] from this [BookList]
 * */
@Composable
fun SuccessScreen(bookList: BookList, bookListItems: List<Book>, onNavigate: (String) -> Unit, onDelete: (Book) -> Unit) {
    Scaffold(
        topBar = {
            Surface(shadowElevation = dimensionResource(R.dimen.padding_small), modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.padding(dimensionResource(R.dimen.padding_small))) {
                    DetailComponent(caption = "List name") {
                        Text(bookList.listName, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        },
    ) { padding ->
        Surface {
            LazyColumn(Modifier.padding(padding)) {
                itemsIndexed(
                    bookListItems,
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
                    if (index < bookListItems.lastIndex) {
                        Divider(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                    }
                }
            }
        }
    }
}
