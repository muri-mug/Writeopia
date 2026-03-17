package io.writeopia.commonui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.writeopia.common.utils.ALLOW_BACKEND
import io.writeopia.common.utils.configuration.LocalPlatform
import io.writeopia.resources.WrStrings

@Composable
fun SettingsPanel(
    accountScreen: @Composable () -> Unit,
    appearanceScreen: @Composable () -> Unit,
    directoryScreen: @Composable () -> Unit,
    aiScreen: @Composable () -> Unit,
    teamsScreen: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    var pageState by remember {
        mutableStateOf(if (ALLOW_BACKEND) SettingsPage.ACCOUNT else SettingsPage.APPEARANCE)
    }

    Row(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.width(180.dp).fillMaxHeight()) {
            val currentPlatform = LocalPlatform.current

            if (ALLOW_BACKEND) {
                SettingsNavItem(WrStrings.account(), SettingsPage.ACCOUNT, pageState) { page ->
                    pageState = page
                }
            }

            SettingsNavItem(WrStrings.appearance(), SettingsPage.APPEARANCE, pageState) { page ->
                pageState = page
            }

            if (currentPlatform.isDesktop()) {
                SettingsNavItem("AI", SettingsPage.AI, pageState) { page ->
                    pageState = page
                }

                SettingsNavItem(
                    WrStrings.workspaceName(),
                    SettingsPage.DIRECTORY,
                    pageState
                ) { page ->
                    pageState = page
                }
            }

            SettingsNavItem(WrStrings.teams(), SettingsPage.TEAMS, pageState) { page ->
                pageState = page
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                WrStrings.version(),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 12.dp),
            )
        }

        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Crossfade(pageState) { page ->
                when (page) {
                    SettingsPage.ACCOUNT -> accountScreen()
                    SettingsPage.APPEARANCE -> appearanceScreen()
                    SettingsPage.DIRECTORY -> directoryScreen()
                    SettingsPage.AI -> aiScreen()
                    SettingsPage.TEAMS -> teamsScreen()
                }
            }
        }
    }
}

@Composable
private fun SettingsNavItem(
    text: String,
    pageState: SettingsPage,
    currentPage: SettingsPage,
    click: (SettingsPage) -> Unit,
) {
    NavigationDrawerItem(
        label = { Text(text, style = MaterialTheme.typography.bodyMedium) },
        selected = currentPage == pageState,
        onClick = { click(pageState) },
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
    )
}

enum class SettingsPage {
    ACCOUNT, APPEARANCE, DIRECTORY, AI, TEAMS,
}
