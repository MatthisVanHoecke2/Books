package com.example.books.ui.screens.booklists.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.booklists.model.BookListModalDBState
import com.example.books.ui.shared.ConfirmDialog

@Composable
fun SuccessScreen(
    bookLists: List<BookList>,
    createDialogText: String,
    onTextChange: (String) -> Unit,
    onDeleteList: (BookList) -> Unit,
    onRenameList: (BookList) -> Unit,
    dbModalState: BookListModalDBState,
    onNavigate: (Long) -> Unit,
    padding: PaddingValues,
) {
    var openRename by remember { mutableStateOf(false) }
    var openDeleteConfirm by remember { mutableStateOf(false) }
    var bookListToRename: BookList? by remember { mutableStateOf(null) }
    var bookListToDelete: BookList? by remember { mutableStateOf(null) }

    if (openDeleteConfirm) {
        ConfirmDialog(onDismiss = { openDeleteConfirm = false }, onConfirm = {
            onDeleteList.invoke(bookListToDelete!!)
            openDeleteConfirm = false
        }, title = { Text("Confirm") }) {
            Text("Are you sure you want to delete the list '${bookListToDelete!!.listName}'")
        }
    }

    if (openRename) {
        BookListModal(
            onDismiss = { openRename = false },
            onConfirm = {
                onRenameList.invoke(bookListToRename!!)
                openRename = false
            },
            onTextChange = onTextChange,
            createDialogText = createDialogText,
            dbModalState = dbModalState,
            title = { Text("Rename list") },
            confirmText = "Rename",
        )
    }

    LazyColumn(modifier = Modifier.padding(padding)) {
        itemsIndexed(bookLists) { index, book ->
            BookListItem(
                bookList = book,
                onTextChange = onTextChange,
                onRenameList = {
                    bookListToRename = it
                    openRename = true
                },
                onDeleteList = {
                    bookListToDelete = it
                    openDeleteConfirm = true
                },
                onNavigate = onNavigate,
            )
            if (index < bookLists.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
            }
        }
    }
}
