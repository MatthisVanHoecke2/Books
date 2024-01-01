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
import androidx.compose.ui.res.stringResource
import com.example.books.R

/**
 * Custom composable [OutlinedTextField]
 * @param modifier value to adjust the default textfield
 * @param value current textfield value
 * @param onValueChange callback to change value
 * @param onDone callback to perform when done
 * @param label caption component shown above textfield
 * @param supportingText component to be shown below textfield
 * @param readOnly value to determine whether textfield is read only
 * @param trailingIcon component shown after textfield
 * @param leadingIcon component shown before textfield
 * @param placeholder placeholder component
 * @param onClear callback to clear textfield
 * @param clearable value to determine whether textfield is clearable
 * @param colors textfield colors
 * @param outlined value to determine if textfield is of the Outlined or Default variant
 * */
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
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
                            Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.textfield_clear_icon))
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
                            Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.textfield_clear_icon))
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
