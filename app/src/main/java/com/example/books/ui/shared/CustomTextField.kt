package com.example.books.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun CustomTextField(
    value: String = "",
    onValueChange: (String) -> Unit,
    onDone: (() -> Unit)? = null,
    label: @Composable() (() -> Unit)? = null,
    supportingText: @Composable() (() -> Unit)? = null,
    readOnly: Boolean = false,
    trailingIcon: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    isError: Boolean = false,
    onClear: (() -> Unit)? = null,
    clearable: Boolean = true,
    modifier: Modifier = Modifier,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    outlined: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    val actions = KeyboardActions(onDone = {
        onDone?.invoke()
        focusManager.clearFocus()
    })

    if (outlined) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            supportingText = supportingText,
            readOnly = readOnly,
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (value.isNotBlank() && clearable) {
                        IconButton(onClick = {
                            onClear?.invoke()
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "clear icon")
                        }
                    }
                    trailingIcon?.invoke()
                }
            },
            leadingIcon = leadingIcon,
            placeholder = placeholder,
            modifier = modifier,
            singleLine = true,
            colors = colors,
            keyboardActions = actions,
            isError = isError,
        )
    } else {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            supportingText = supportingText,
            readOnly = readOnly,
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (value.isNotBlank() && clearable) {
                        IconButton(onClick = {
                            onClear?.invoke()
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "clear icon")
                        }
                    }
                    trailingIcon?.invoke()
                }
            },
            leadingIcon = leadingIcon,
            placeholder = placeholder,
            modifier = modifier,
            singleLine = true,
            colors = colors,
            keyboardActions = actions,
            isError = isError,
        )
    }
}
