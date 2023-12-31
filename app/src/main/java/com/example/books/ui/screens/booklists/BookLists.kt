package com.example.books.ui.screens.booklists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.books.R
import com.example.books.persistence.data.booklists.BookList
import com.example.books.ui.screens.booklists.components.BookListModal
import com.example.books.ui.screens.booklists.components.ErrorScreen
import com.example.books.ui.screens.booklists.components.LoadingScreen
import com.example.books.ui.screens.booklists.components.SuccessScreen
import com.example.books.ui.screens.booklists.model.BookListGetDBState
import com.example.books.ui.screens.booklists.model.BookListViewModel

/**
 * Composable screen for displaying the BookLists page
 * @param onNavigate callback function for navigating to a BookListDetails page
 * */
@Composable
fun BookLists(onNavigate: (Long) -> Unit) {
    val viewModel = viewModel<BookListViewModel>(factory = BookListViewModel.Factory)
    val bookListUiState by viewModel.bookListUiState.collectAsState()
    val bookLists = bookListUiState.bookLists
    val openDialog = bookListUiState.openCreateDialog
    val createDialogText = bookListUiState.createDialogText
    val dbState = viewModel.bookListDBState
    val dbModalState = viewModel.bookListModalDBState
    val isValidListName = viewModel.validateName(createDialogText)

    if (openDialog) {
        BookListModal(
            onDismiss = { viewModel.onOpenCreateDialog(false) },
            onConfirm = {
                if (isValidListName) {
                    viewModel.createList(createDialogText)
                }
            },
            onTextChange = { viewModel.onTextInput(it) },
            dialogText = createDialogText,
            dbModalState = dbModalState,
            title = { Text(stringResource(R.string.booklist_createmodal_confirm_title)) },
            confirmText = stringResource(R.string.booklist_createmodal_button_create),
            isError = !isValidListName,
        )
    }

    Scaffold(
        topBar = {
            Surface(shadowElevation = dimensionResource(R.dimen.padding_small), modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.padding(0.dp, dimensionResource(R.dimen.padding_small))) {
                    Button(onClick = { viewModel.onOpenCreateDialog(true) }) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.booklist_createlist_icon))
                        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(R.string.booklist_button_createlist))
                    }
                }
            }
        },
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            when (dbState) {
                is BookListGetDBState.Success -> SuccessScreen(
                    bookLists = bookLists,
                    createDialogText = createDialogText,
                    onTextChange = { viewModel.onTextInput(it) },
                    onDeleteList = { viewModel.deleteList(it) },
                    onRenameList = { viewModel.updateList(BookList(it.bookListId, createDialogText)) },
                    dbModalState = dbModalState,
                    onNavigate = onNavigate,
                    isValidListName = isValidListName,
                )
                is BookListGetDBState.Loading -> LoadingScreen()
                is BookListGetDBState.Error -> ErrorScreen(dbState.message)
            }
        }
    }
}
