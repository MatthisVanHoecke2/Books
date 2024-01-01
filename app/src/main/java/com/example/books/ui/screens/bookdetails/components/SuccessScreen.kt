package com.example.books.ui.screens.bookdetails.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import com.example.books.R
import com.example.books.model.Book
import com.example.books.network.data.books.BookDetail
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.bookdetails.model.BookInsertApiState
import com.example.books.ui.shared.CustomAlertDialog
import com.example.books.ui.shared.DetailComponent

/**
 * Screen to display when the [Book] data has been retrieved successfully
 * @param book the currently selected [Book]
 * @param ratings the selected [Book] ratings
 * @param bookLists list of [BookList] to choose from, used to add the current [Book] to a specific list
 * @param insertBookIntoList callback function for adding the current displayed [Book] to a [BookList]
 * @param apiState the current state of the insert operation
 * @param closeAlert callback function for closing the message dialog for displaying a success or error message
 * */
@Composable
fun SuccessScreen(book: Book, ratings: Double, bookLists: List<BookList>, insertBookIntoList: (BookList) -> Unit, apiState: BookInsertApiState, closeAlert: () -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current

    when (apiState) {
        is BookInsertApiState.Start -> {}
        is BookInsertApiState.Success -> {
            CustomAlertDialog(
                title = { Text(stringResource(R.string.bookdetails_addtolist_success_title), style = MaterialTheme.typography.titleLarge) },
                text = { Text(apiState.message) },
                onOk = { closeAlert.invoke() },
            )
        }
        is BookInsertApiState.Error -> {
            CustomAlertDialog(
                title = { Text(stringResource(R.string.bookdetails_addtolist_error_title), style = MaterialTheme.typography.titleLarge) },
                text = { Text(apiState.message) },
                onOk = { closeAlert.invoke() },
            )
        }
    }

    if (openDialog) {
        ListDialog(onDismiss = { openDialog = false }, bookLists = bookLists, insertBookIntoList = insertBookIntoList)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
        modifier = Modifier.padding(
            dimensionResource(R.dimen.padding_medium),
        ).verticalScroll(rememberScrollState()),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = Modifier.fillMaxWidth(0.5f),
        ) {
            DetailComponent(caption = stringResource(R.string.bookdetails_caption_title), content = { Text(book.title) })
            DetailComponent(caption = stringResource(R.string.bookdetails_caption_ratings), content = { Text("${String.format("%.1f", ratings)}/5") })
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                CoverImage(book)
            }
            Button(onClick = { openDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.bookdetails_add_icon))
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(R.string.bookdetails_add_buttontext))
            }
        }
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                CoverImage(book)
            }
        }
    }
}

@Composable
private fun CoverImage(book: Book) {
    if (book.coverId != null || (book is BookDetail && book.covers.isNotEmpty())) {
        val cover = if (book.coverId != null) book.coverId else (book as BookDetail).covers.first()
        val imageUrl = "https://covers.openlibrary.org/b/id/$cover-L.jpg"
        Box(
            modifier = Modifier
                .width(dimensionResource(R.dimen.cover_width))
                .height(dimensionResource(R.dimen.cover_height)),
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = book.title,
                loading = { Text(book.title) },
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
