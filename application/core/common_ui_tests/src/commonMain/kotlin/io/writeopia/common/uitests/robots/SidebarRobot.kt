package io.writeopia.common.uitests.robots

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick

/**
 * Robot for the [SideGlobalMenu] sidebar component.
 *
 * All test tag constants mirror the values defined in SideGlobalMenu.kt.
 */
object SidebarRobot {

    // ── Actions ──────────────────────────────────────────────────────────────

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.toggleSidebar() {
        onNodeWithTag("sideMenuToggle").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.clickSearch() {
        onNodeWithTag("sideMenuSearch").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.clickHome() {
        onNodeWithTag("sideMenuHome").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.clickFavorites() {
        onNodeWithTag("sideMenuFavorites").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.clickSettings() {
        onNodeWithTag("sideMenuSettings").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.clickHelp() {
        onNodeWithTag("sideMenuHelp").performClick()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.clickLogout() {
        onNodeWithTag("sideMenuLogout").performClick()
    }

    // ── Assertions ───────────────────────────────────────────────────────────

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertSidebarToggleVisible() {
        onNodeWithTag("sideMenuToggle").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertHomeVisible() {
        onNodeWithTag("sideMenuHome").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertFavoritesVisible() {
        onNodeWithTag("sideMenuFavorites").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertSettingsVisible() {
        onNodeWithTag("sideMenuSettings").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertSearchVisible() {
        onNodeWithTag("sideMenuSearch").assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun ComposeUiTest.assertLogoutVisible() {
        onNodeWithTag("sideMenuLogout").assertIsDisplayed()
    }
}
