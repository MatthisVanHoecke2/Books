package com.example.books.ui.screens.booklists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.R
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.booklists.components.BookListItem
import com.example.books.ui.screens.booklists.components.BookListModal
import com.example.books.ui.screens.booklists.model.BookListDBState
import com.example.books.ui.screens.booklists.model.BookListModalDBState
import com.example.books.ui.screens.booklists.model.BookListViewModel

@Composable
fun BookLists(onNavigate: (Long) -> Unit) {
    val viewModel = viewModel<BookListViewModel>(factory = BookListViewModel.Factory)
    val bookListUiState by viewModel.bookListUiState.collectAsState()
    val bookLists = bookListUiState.bookLists
    val openDialog = bookListUiState.openCreateDialog
    val createDialogText = bookListUiState.createDialogText
    val dbState = viewModel.bookListDBState
    val dbModalState = viewModel.bookListModalDBState

    if (openDialog) {
        BookListModal(
            onDismiss = { viewModel.openCreateDialog(false) },
            onConfirm = { viewModel.createList(createDialogText) },
            onTextChange = { viewModel.onTextInput(it) },
            createDialogText = createDialogText,
            dbModalState = dbModalState,
            title = { Text("Create list") },
            confirmText = "Create",
        )
    }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
                Box {
                    Button(onClick = { viewModel.openCreateDialog(true) }) {
                        Icon(Icons.Default.Add, contentDescription = "Create list icon")
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Create list")
                    }
                }
            }
        },
    ) { padding ->
        when (dbState) {
            is BookListDBState.Success -> SuccessScreen(
                bookLists = bookLists,
                createDialogText = createDialogText,
                onTextChange = { viewModel.onTextInput(it) },
                onDeleteList = { viewModel.deleteList(it) },
                onRenameList = { viewModel.updateList(BookList(it.bookListId, createDialogText)) },
                dbModalState = dbModalState,
                onNavigate = onNavigate,
                padding = padding,
            )
            is BookListDBState.Loading -> LoadingScreen()
            is BookListDBState.Error -> ErrorScreen()
        }
    }
}

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
    LazyColumn(modifier = Modifier.padding(padding)) {
        itemsIndexed(bookLists) { index, book ->
            BookListItem(
                bookList = book,
                createDialogText = createDialogText,
                onTextChange = onTextChange,
                onRenameList = onRenameList,
                onDeleteList = onDeleteList,
                dbModalState = dbModalState,
                onNavigate = onNavigate,
            )
            if (index < bookLists.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun ErrorScreen() {
    Text("An error occurred while fetching the book lists")
}
