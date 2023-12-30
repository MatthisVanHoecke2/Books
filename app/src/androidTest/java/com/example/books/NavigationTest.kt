package com.example.books

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.books.ui.BooksApp
import com.example.books.ui.navigation.AppPage
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            BooksApp(navController = navController)
        }
    }

    @Test
    fun booksNavHost_verifyStartDestination() {
        val route = composeTestRule.activity.getString(AppPage.Home.route)
        assertEquals(route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun booksNavHost_clickNavDrawerButton_opensNavDrawer() {
        val bookListsName = composeTestRule.activity.getString(AppPage.BookLists.displayName)
        composeTestRule.onNodeWithTag("menuButton").performClick()
        composeTestRule.onNodeWithText(bookListsName).assertExists().assertIsDisplayed()
    }

    @Test
    fun booksNavHost_clickNavDrawerButton_clickBookLists_navigatesToBookLists() {
        val bookListsName = composeTestRule.activity.getString(AppPage.BookLists.displayName)
        val bookListsRoute = composeTestRule.activity.getString(AppPage.BookLists.route)
        composeTestRule.onNodeWithTag("menuButton").performClick()
        composeTestRule.onNodeWithText(bookListsName).performClick()
        navController.assertCurrentRouteName(bookListsRoute)
    }
}
