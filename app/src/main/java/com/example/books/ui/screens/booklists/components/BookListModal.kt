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
import com.example.books.R
import com.example.books.ui.screens.booklists.model.BookListModalDBState
import com.example.books.ui.shared.ConfirmDialog
import com.example.books.ui.shared.CustomTextField

@Composable
fun BookListModal(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onTextChange: (String) -> Unit,
    createDialogText: String,
    dbModalState: BookListModalDBState,
    title: @Composable() () -> Unit,
    confirmText: String,
) {
    ConfirmDialog(
        icon = {
            if (dbModalState is BookListModalDBState.Loading) {
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
                    value = createDialogText,
                    onValueChange = { onTextChange.invoke(it) },
                    onDone = { onConfirm.invoke() },
                    placeholder = { Text("List name") },
                    outlined = true,
                )
                if (dbModalState is BookListModalDBState.Error) {
                    Text(dbModalState.message)
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm.invoke() }, enabled = dbModalState !is BookListModalDBState.Loading) {
                Text(confirmText)
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss.invoke() }, enabled = dbModalState !is BookListModalDBState.Loading) {
                Text("Cancel")
            }
        },
    )
}
