package com.example.books.ui.screens.booklistdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.books.R

/**
 * Screen to display when the repository request fails
 * @param message error message
 * */
@Composable
fun ErrorScreen(message: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        Text(message)
    }
}
