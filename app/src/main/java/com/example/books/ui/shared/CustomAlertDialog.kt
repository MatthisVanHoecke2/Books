package com.example.books.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.window.Dialog
import com.example.books.R

/**
 * Custom composable alert dialog
 * @param title composable dialog title
 * @param text composable dialog text
 * @param onOk callback function to be performed when 'Ok' is clicked
 * */
@Composable
fun CustomAlertDialog(
    title: @Composable() () -> Unit,
    text: @Composable() () -> Unit,
    onOk: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onOk.invoke() },
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            shadowElevation = dimensionResource(R.dimen.padding_small),
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large)),
            ) {
                Icon(Icons.Default.Info, "dialog icon")
                title.invoke()
                text.invoke()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Box {
                        Button(onClick = { onOk.invoke() }) {
                            Text("Ok")
                        }
                    }
                }
            }
        }
    }
}
