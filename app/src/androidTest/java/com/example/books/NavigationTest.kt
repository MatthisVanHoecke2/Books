package com.example.books

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
        val menuButton = composeTestRule.activity.getString(R.string.menu_button)
        composeTestRule.onNodeWithContentDescription(menuButton).performClick()
        composeTestRule.onNodeWithText(bookListsName).assertExists().assertIsDisplayed()
    }

    @Test
    fun booksNavHost_clickNavDrawerButton_clickBookLists_navigatesToBookLists() {
        val bookListsName = composeTestRule.activity.getString(AppPage.BookLists.displayName)
        val bookListsRoute = composeTestRule.activity.getString(AppPage.BookLists.route)
        val menuButton = composeTestRule.activity.getString(R.string.menu_button)
        composeTestRule.onNodeWithContentDescription(menuButton).performClick()
        composeTestRule.onNodeWithText(bookListsName).performClick()
        navController.assertCurrentRouteName(bookListsRoute)
    }

    @Test
    fun booksNavHost_clickNavDrawerButton_clickBookLists_navigatesToBookLists_clickHome_navigatesToHome() {
        val bookListsName = composeTestRule.activity.getString(AppPage.BookLists.displayName)
        val bookListsRoute = composeTestRule.activity.getString(AppPage.BookLists.route)
        val menuButton = composeTestRule.activity.getString(R.string.menu_button)
        composeTestRule.onNodeWithContentDescription(menuButton).performClick()
        composeTestRule.onNodeWithText(bookListsName).performClick()
        navController.assertCurrentRouteName(bookListsRoute)

        val homeName = composeTestRule.activity.getString(AppPage.Home.displayName)
        val homeRoute = composeTestRule.activity.getString(AppPage.Home.route)
        composeTestRule.onNodeWithContentDescription(menuButton).performClick()
        composeTestRule.onNodeWithText(homeName).performClick()
        navController.assertCurrentRouteName(homeRoute)
    }

    @Test
    fun booksNavHost_verifyBackNavigationNotShownOnStart() {
        val menuButton = composeTestRule.activity.getString(R.string.menu_button)
        val backButton = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(menuButton).assertExists()
        composeTestRule.onNodeWithContentDescription(backButton).assertDoesNotExist()
    }

    @Test
    fun booksNavHost_navigateToBookLists_verifyBackNavigationNotShown() {
        val bookListsName = composeTestRule.activity.getString(AppPage.BookLists.displayName)
        val bookListsRoute = composeTestRule.activity.getString(AppPage.BookLists.route)
        val menuButton = composeTestRule.activity.getString(R.string.menu_button)
        val backButton = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(menuButton).performClick()
        composeTestRule.onNodeWithText(bookListsName).performClick()
        navController.assertCurrentRouteName(bookListsRoute)

        composeTestRule.onNodeWithContentDescription(menuButton).assertExists()
        composeTestRule.onNodeWithContentDescription(backButton).assertDoesNotExist()
    }
}
