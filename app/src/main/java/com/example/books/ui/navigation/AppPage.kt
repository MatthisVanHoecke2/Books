package com.example.books.ui.navigation

import androidx.annotation.StringRes
import com.example.books.R

enum class AppPage(@StringRes val route: Int, @StringRes val displayName: Int, val canBack: Boolean, val showId: Boolean) {
    Home(route = R.string.home_route, displayName = R.string.home_name, canBack = false, showId = false),
}
