package com.example.books.ui.navigation

import androidx.annotation.StringRes
import com.example.books.R

enum class AppPage(@StringRes val route: Int, @StringRes val displayName: Int) {
    Home(route = R.string.home_route, displayName = R.string.home_name),
    BookDetails(route = R.string.bookdetails_route, displayName = R.string.bookdetails_name),
    BookLists(route = R.string.booklists_route, displayName = R.string.booklists_name),
    BookListDetails(route = R.string.booklistdetails_route, displayName = R.string.booklistdetails_name),
}
