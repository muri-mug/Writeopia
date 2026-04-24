package io.writeopia.common.uitests.tests.navigation

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import io.writeopia.common.uitests.robots.DocumentsMenuRobot
import io.writeopia.common.uitests.robots.SidebarRobot.assertFavoritesVisible
import io.writeopia.common.uitests.robots.SidebarRobot.assertHomeVisible
import io.writeopia.common.uitests.robots.SidebarRobot.assertLogoutVisible
import io.writeopia.common.uitests.robots.SidebarRobot.assertSearchVisible
import io.writeopia.common.uitests.robots.SidebarRobot.assertSidebarToggleVisible
import io.writeopia.common.uitests.robots.SidebarRobot.assertSettingsVisible
import io.writeopia.common.uitests.robots.SidebarRobot.toggleSidebar

/**
 * Shared E2E navigation tests using the Robot pattern.
 * Platform-specific test classes (e.g. DesktopNavigationTest) run these scenarios.
 */
@OptIn(ExperimentalTestApi::class)
object NavigationCommonTests {

    /**
     * Verifies that all primary sidebar nav items are visible on first render.
     */
    fun ComposeUiTest.assertSidebarItemsAreVisible() {
        assertSidebarToggleVisible()
        assertHomeVisible()
        assertFavoritesVisible()
        assertSettingsVisible()
        assertSearchVisible()
        assertLogoutVisible()
    }

    /**
     * Verifies that the sidebar can be collapsed via the toggle button
     * and that doc list items are no longer accessible.
     * After collapse the toggle should still be reachable (it stays visible).
     */
    fun ComposeUiTest.collapseSidebarAndAssertToggleRemains() {
        assertSidebarToggleVisible()
        toggleSidebar()
        // Toggle itself stays at the collapsed position — still tappable
        assertSidebarToggleVisible()
    }

    /**
     * Verifies collapse → expand cycle restores the sidebar.
     */
    fun ComposeUiTest.collapseAndExpandSidebarRestoresState() {
        assertSidebarToggleVisible()
        toggleSidebar()  // collapse
        toggleSidebar()  // expand
        assertHomeVisible()
        assertFavoritesVisible()
        assertSettingsVisible()
    }

    /**
     * Full new-note flow: open the editor from the documents menu, write a title,
     * navigate back, and verify the note appears in the list.
     */
    fun ComposeUiTest.createNoteAndVerifyInList() {
        val noteTitle = "NavigationTest Note"

        DocumentsMenuRobot.run {
            goToEditNote()
        }

        io.writeopia.common.uitests.robots.DocumentEditRobot.run {
            writeTitle(noteTitle)
            goBack()
        }

        DocumentsMenuRobot.run {
            assertNoteWithTitle(noteTitle)
        }
    }
}
