package com.example.books.ui.navigation

import androidx.annotation.StringRes
import com.example.books.R

enum class AppPage(@StringRes val route: Int, @StringRes val displayName: Int, val canBack: Boolean) {
    Home(route = R.string.home_route, displayName = R.string.home_name, canBack = false),
    BookDetails(route = R.string.bookdetails_route, displayName = R.string.bookdetails_name, canBack = true),
}
