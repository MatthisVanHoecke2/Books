package com.example.books.ui.screens.booklists.components

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
import androidx.compose.ui.res.stringResource
import com.example.books.R
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.booklists.model.BookListCreateUpdateDBState
import com.example.books.ui.shared.ConfirmDialog

/**
 * Screen to display when the list of [BookList] has been retrieved successfully
 * @param bookLists list of [BookList]
 * @param createDialogText create dialog textfield value
 * @param onTextChange callback for changing the [createDialogText] value
 * @param onDeleteList callback function for deleting a [BookList]
 * @param onRenameList callback for renaming a book list
 * @param dbModalState current repository state for the book list create operation
 * @param onNavigate callback function for navigating to a book list detail page
 * */
@Composable
fun SuccessScreen(
    bookLists: List<BookList>,
    createDialogText: String,
    onTextChange: (String) -> Unit,
    isValidListName: Boolean,
    onDeleteList: (BookList) -> Unit,
    onRenameList: (BookList) -> Unit,
    dbModalState: BookListCreateUpdateDBState,
    onNavigate: (Long) -> Unit,
) {
    var openRename by remember { mutableStateOf(false) }
    var openDeleteConfirm by remember { mutableStateOf(false) }
    var bookListToRename: BookList? by remember { mutableStateOf(null) }
    var bookListToDelete: BookList? by remember { mutableStateOf(null) }

    if (openDeleteConfirm) {
        ConfirmDialog(onDismiss = { openDeleteConfirm = false }, onConfirm = {
            onDeleteList.invoke(bookListToDelete!!)
            openDeleteConfirm = false
        }, title = { Text(stringResource(R.string.confirmdialog_confirm_title)) }) {
            Text("Are you sure you want to delete the list '${bookListToDelete!!.listName}'")
        }
    }

    if (openRename) {
        BookListModal(
            onDismiss = { openRename = false },
            onConfirm = {
                if (isValidListName) {
                    onRenameList.invoke(bookListToRename!!)
                    openRename = false
                }
            },
            onTextChange = onTextChange,
            dialogText = createDialogText,
            dbModalState = dbModalState,
            title = { Text(stringResource(R.string.booklist_renamemodal_confirm_title)) },
            confirmText = stringResource(R.string.booklist_renamemodal_button_rename),
            isError = !isValidListName,
        )
    }

    LazyColumn {
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
