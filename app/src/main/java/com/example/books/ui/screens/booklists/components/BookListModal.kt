package com.example.books.ui.screens.booklists.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.books.R
import com.example.books.ui.screens.booklists.model.BookListCreateUpdateDBState
import com.example.books.ui.shared.ConfirmDialog
import com.example.books.ui.shared.CustomTextField

/**
 * Composable modal for creating or renaming a book list
 * @param onDismiss callback for closing the modal
 * @param onConfirm callback for confirming the creation or change
 * @param onTextChange callback for editing the [dialogText]
 * @param dialogText modal textfield value
 * @param dbModalState the current state of an create or update operation
 * @param title composable title of the modal
 * @param confirmText the text inside the confirm button
 * */
@Composable
fun BookListModal(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onTextChange: (String) -> Unit,
    dialogText: String,
    dbModalState: BookListCreateUpdateDBState,
    title: @Composable() () -> Unit,
    confirmText: String,
    isError: Boolean
) {
    ConfirmDialog(
        icon = {
            if (dbModalState is BookListCreateUpdateDBState.Loading) {
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
        },
        onDismiss = { onDismiss.invoke() },
        onConfirm = { onConfirm.invoke() },
        title = title,
        content = {
            Column {
                CustomTextField(
                    value = dialogText,
                    onValueChange = { onTextChange.invoke(it) },
                    onDone = { onConfirm.invoke() },
                    onClear = { onTextChange.invoke("") },
                    placeholder = { Text(stringResource(R.string.booklistdetails_caption_listname)) },
                    outlined = true,
                    isError = isError,
                )
                if (dbModalState is BookListCreateUpdateDBState.Error) {
                    Text(dbModalState.message)
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm.invoke() }, enabled = dbModalState !is BookListCreateUpdateDBState.Loading) {
                Text(confirmText)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss.invoke() }, enabled = dbModalState !is BookListCreateUpdateDBState.Loading) {
                Text(stringResource(R.string.confirmdialog_button_cancel))
            }
        },
    )
}
