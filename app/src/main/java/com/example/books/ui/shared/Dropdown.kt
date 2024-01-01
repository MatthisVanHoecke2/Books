package com.example.books.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.books.R

/**
 * Custom dropdown component for selecting a [DropdownItem]
 * @param list list of selectable values
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOptionsDropDown(list: List<DropdownItem>) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        // sorting dropdown menu
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.menuAnchor(),
        ) {
            Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.delete_icon))
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(125.dp)
                .background(MaterialTheme.colorScheme.onPrimary),
        ) { // contents of dropdown menu
            for (item: DropdownItem in list) {
                DropdownMenuItem(text = { Text(item.name) }, onClick = {
                    item.callback.invoke()
                    expanded = false
                })
            }
        }
    }
}

/**
 * Data class for creating a list of selectable items
 * @property name the text displayed within an item
 * @property callback the function executed on click
 * */
data class DropdownItem(val name: String, val callback: () -> Unit)
