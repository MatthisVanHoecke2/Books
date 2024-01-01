package com.example.books.ui.screens.bookdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.books.R
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.shared.ConfirmDialog

/**
 * Dialog for adding a book to a specific book list
 * @param onDismiss callback function for closing the dialog
 * @param bookLists list of [BookList] to choose from
 * @param insertBookIntoList callback function for adding the current displayed book to a book list
 * */
@Composable
fun ListDialog(onDismiss: () -> Unit, bookLists: List<BookList>, insertBookIntoList: (BookList) -> Unit) {
    ConfirmDialog(
        icon = { },
        onDismiss = { onDismiss.invoke() },
        onConfirm = { onDismiss.invoke() },
        title = { Text(stringResource(R.string.bookdetails_addtolist_dialog_title), style = MaterialTheme.typography.titleLarge) },
        dismissButton = { },
        confirmButton = { },
        modifier = Modifier.heightIn(0.dp, 200.dp),
    ) {
        LazyColumn {
            items(bookLists) {
                ListItem(
                    headlineContent = { Text(it.listName) },
                    modifier = Modifier.clickable {
                        insertBookIntoList.invoke(it)
                        onDismiss.invoke()
                    },
                )
            }
        }
    }
}
