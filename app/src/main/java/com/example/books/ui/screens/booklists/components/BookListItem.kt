package com.example.books.ui.screens.booklists.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.books.R
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.shared.CustomOptionsDropDown
import com.example.books.ui.shared.DropdownItem

/**
 * Composable for displaying a single [BookList]
 * @param bookList current [BookList] to display
 * @param onTextChange callback for changing the create modal dialog textfield value
 * @param onRenameList callback for renaming a book list
 * @param onDeleteList callback for deleting a book list
 * @param onNavigate callback for navigating to the book list details page
 * */
@Composable
fun BookListItem(
    bookList: BookList,
    onTextChange: (String) -> Unit,
    onRenameList: (BookList) -> Unit,
    onDeleteList: (BookList) -> Unit,
    onNavigate: (Long) -> Unit,
) {
    ListItem(
        leadingContent = { Icon(Icons.Default.FormatListBulleted, contentDescription = "booklist ${bookList.listName}") },
        headlineContent = { Text(bookList.listName) },
        trailingContent = {
            CustomOptionsDropDown(
                list = listOf(
                    DropdownItem("Rename", callback = {
                        onTextChange.invoke(bookList.listName)
                        onRenameList.invoke(bookList)
                    }),
                    DropdownItem("Delete", callback = { onDeleteList.invoke(bookList) }),
                ),
            )
        },
        modifier = Modifier.padding(0.dp, dimensionResource(R.dimen.padding_small)).clickable { onNavigate.invoke(bookList.bookListId) },
    )
}
