package com.example.books.ui.navigation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.books.R

/**
 * Composable drawer item, used for navigating to different pages
 * @param icon icon to display in drawer item
 * @param label label display name of the page to navigate to
 * @param onClick callback function for navigating to a page when clicked
 * */
@Composable
fun DrawerItem(
    icon: ImageVector? = null,
    label: String,
    onClick: () -> Unit,
) {
    val ph = dimensionResource(R.dimen.padding_medium)
    val pv = dimensionResource(R.dimen.padding_large)

    Box(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick.invoke()
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(ph, pv),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = "drawer icon",
                    Modifier.size(
                        dimensionResource(R.dimen.icon_medium),
                    ),
                )
            }
            Text(
                label,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
            )
        }
    }
}
