package com.example.books.ui.screens.booklists.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.books.R
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.booklists.model.BookListModalDBState
import com.example.books.ui.shared.ConfirmDialog
import com.example.books.ui.shared.CustomOptionsDropDown
import com.example.books.ui.shared.DropdownItem

@Composable
fun BookListItem(
    bookList: BookList,
    createDialogText: String,
    onTextChange: (String) -> Unit,
    onRenameList: (BookList) -> Unit,
    onDeleteList: (BookList) -> Unit,
    dbModalState: BookListModalDBState,
    onNavigate: (Long) -> Unit,
) {
    var openDeleteConfirm by remember { mutableStateOf(false) }
    var openRename by remember { mutableStateOf(false) }

    if (openRename) {
        BookListModal(
            onDismiss = { openRename = false },
            onConfirm = {
                onRenameList.invoke(bookList)
                openRename = false
            },
            onTextChange = onTextChange,
            createDialogText = createDialogText,
            dbModalState = dbModalState,
            title = { Text("Rename list") },
            confirmText = "Rename",
        )
    }
    if (openDeleteConfirm) {
        ConfirmDialog(onDismiss = { openDeleteConfirm = false }, onConfirm = {
            onDeleteList.invoke(bookList)
            openDeleteConfirm = false
        }, title = { Text("Confirm") }) {
            Text("Are you sure you want to delete the list '${bookList.listName}'")
        }
    }

    ListItem(
        leadingContent = { Icon(Icons.Default.FormatListBulleted, contentDescription = "booklist ${bookList.listName}") },
        headlineContent = { Text(bookList.listName) },
        trailingContent = {
            CustomOptionsDropDown(
                list = listOf(
                    DropdownItem("Rename", callback = {
                        onTextChange.invoke(bookList.listName)
                        openRename = true
                    }),
                    DropdownItem("Delete", callback = { openDeleteConfirm = true }),
                ),
            )
        },
        modifier = Modifier.padding(0.dp, dimensionResource(R.dimen.padding_small)).clickable { onNavigate.invoke(bookList.bookListId) },
    )
}
