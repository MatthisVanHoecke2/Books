package com.example.books.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.books.R

/**
 * Custom composable confirm dialog
 * @param modifier value for adjusting the default [Dialog]
 * @param icon icon to display above text
 * @param onDismiss callback for closing the dialog
 * @param onConfirm callback for confirming
 * @param confirmButton button to execute the [onConfirm] callback
 * @param dismissButton button to execute the [onDismiss] callback
 * @param title composable title
 * @param content composable content
 * */
@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    icon: @Composable() () -> Unit = { Icon(Icons.Default.Info, contentDescription = stringResource(R.string.dialog_icon)) },
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmButton: @Composable() () -> Unit = {
        Button(onClick = { onConfirm.invoke() }) {
            Text(stringResource(R.string.confirmdialog_button_confirm))
        }
    },
    dismissButton: @Composable() () -> Unit = {
        Button(onClick = { onDismiss.invoke() }) {
            Text(stringResource(R.string.confirmdialog_button_cancel))
        }
    },
    title: @Composable() () -> Unit,
    content: @Composable() () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss.invoke() },
    ) {
        Surface(
            modifier = modifier,
            shape = MaterialTheme.shapes.large,
            shadowElevation = dimensionResource(R.dimen.padding_small),
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
            ) {
                icon.invoke()
                title.invoke()
                content.invoke()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))) {
                        dismissButton.invoke()
                        confirmButton.invoke()
                    }
                }
            }
        }
    }
}
