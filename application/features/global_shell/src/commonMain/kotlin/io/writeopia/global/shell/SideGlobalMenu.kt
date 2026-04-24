package io.writeopia.global.shell

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.writeopia.common.utils.icons.IconChange
import io.writeopia.common.utils.icons.WrIcons
import io.writeopia.commonui.dtos.MenuItemUi
import io.writeopia.commonui.folders.documentList
import io.writeopia.resources.WrStrings
import io.writeopia.sdk.models.user.WriteopiaUser
import io.writeopia.theme.WriteopiaTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val FINAL_WIDTH = 256
private const val LABEL_ANIM_DURATION = 180

@Composable
fun SideGlobalMenu(
    modifier: Modifier = Modifier,
    foldersState: StateFlow<List<MenuItemUi>>,
    userState: StateFlow<WriteopiaUser>,
    background: Color = WriteopiaTheme.colorScheme.globalBackground,
    width: Dp = FINAL_WIDTH.dp,
    searchClick: () -> Unit,
    homeClick: () -> Unit,
    favoritesClick: () -> Unit,
    forceGraphClick: () -> Unit,
    trashClick: () -> Unit,
    settingsClick: () -> Unit,
    addFolder: () -> Unit,
    highlightContent: () -> Unit,
    editFolder: (MenuItemUi.FolderUi) -> Unit,
    navigateToFolder: (String) -> Unit,
    navigateToEditDocument: (String, String) -> Unit,
    moveRequest: (MenuItemUi, String) -> Unit,
    expandFolder: (String) -> Unit,
    changeIcon: (String, String, Int, IconChange) -> Unit,
    toggleMaxScreen: () -> Unit,
    toggleSideMenu: () -> Unit = {},
    helpClick: () -> Unit = {},
    logoutClick: () -> Unit,
) {
    val widthState by derivedStateOf { width }
    val widthAnimatedState by animateDpAsState(widthState)
    val showContent by derivedStateOf {
        when {
            widthState > 80.dp -> ShowContent.FULL
            widthState > 40.dp -> ShowContent.ICONS
            else -> ShowContent.HIDE
        }
    }

    Box(
        modifier = modifier.background(background),
    ) {
        Box(
            modifier = Modifier
                .width(widthAnimatedState)
                .fillMaxHeight()
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            if (showContent != ShowContent.HIDE) {
                val user by userState.collectAsState()
                val menuItems by foldersState.collectAsState()

                Column(modifier = Modifier.fillMaxHeight()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = if (showContent == ShowContent.ICONS) {
                            Alignment.CenterHorizontally
                        } else {
                            Alignment.Start
                        },
                        contentPadding = PaddingValues(bottom = 8.dp),
                    ) {
                        item {
                            UserProfileItem(
                                user = user,
                                showContent = showContent,
                                toggleMaxScreen = toggleMaxScreen,
                            )
                        }

                        item {
                            SideSearchBar(
                                showContent = showContent,
                                onClick = searchClick,
                            )
                        }

                        item { SectionHeader(text = "MAIN", showContent = showContent) }

                        item {
                            SideNavItem(
                                showContent = showContent,
                                iconVector = WrIcons.home,
                                contentDescription = WrStrings.home(),
                                text = WrStrings.home(),
                                click = homeClick,
                                modifier = Modifier.semantics { testTag = "sideMenuHome" },
                            )
                        }

                        item {
                            SideNavItem(
                                showContent = showContent,
                                iconVector = WrIcons.favorites,
                                contentDescription = WrStrings.favorites(),
                                text = WrStrings.favorites(),
                                click = favoritesClick,
                                modifier = Modifier.semantics { testTag = "sideMenuFavorites" },
                            )
                        }

                        if (showContent == ShowContent.FULL) {
                            item {
                                FolderSectionHeader(
                                    highlightContent = highlightContent,
                                    addFolder = addFolder,
                                )
                            }

                            documentList(
                                menuItems = menuItems,
                                editFolder = editFolder,
                                selectedFolder = navigateToFolder,
                                selectedDocument = navigateToEditDocument,
                                moveRequest = moveRequest,
                                expandFolder = expandFolder,
                                changeIcon = changeIcon
                            )
                        }

                        item {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.outlineVariant,
                            )
                        }

                        item { SectionHeader(text = "SETTINGS", showContent = showContent) }

                        item {
                            SideNavItem(
                                showContent = showContent,
                                iconVector = WrIcons.settings,
                                contentDescription = WrStrings.settings(),
                                text = WrStrings.settings(),
                                click = settingsClick,
                                modifier = Modifier.semantics { testTag = "sideMenuSettings" },
                            )
                        }
                    }

                    BottomActions(
                        showContent = showContent,
                        helpClick = helpClick,
                        logoutClick = logoutClick,
                    )
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(x = widthAnimatedState - 12.dp, y = 28.dp)
                .size(24.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.small)
                .semantics { testTag = "sideMenuToggle" }
                .clickable(onClick = toggleSideMenu)
        ) {
            Icon(
                imageVector = WrIcons.smallArrowRight,
                contentDescription = if (showContent == ShowContent.FULL) "Collapse menu" else "Expand menu",
                modifier = Modifier
                    .size(14.dp)
                    .graphicsLayer {
                        rotationZ = if (showContent == ShowContent.FULL) 180f else 0f
                    },
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// ─── Search bar ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SideSearchBar(
    showContent: ShowContent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showContent == ShowContent.HIDE) return
    val isFull = showContent == ShowContent.FULL

    if (!isFull) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
            tooltip = { PlainTooltip { Text("Search") } },
            state = rememberTooltipState(),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .size(44.dp)
                    .clip(MaterialTheme.shapes.small)
                    .semantics { testTag = "sideMenuSearch" }
                    .clickable(onClick = onClick),
            ) {
                Icon(
                    imageVector = WrIcons.search,
                    contentDescription = "Search",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .semantics { testTag = "sideMenuSearch" }
                .clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Icon(
                imageVector = WrIcons.search,
                contentDescription = "Search",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Search...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            )
        }
    }
}

// ─── User profile ───────────────────────────────────────────────────────────

@Composable
private fun UserProfileItem(
    user: WriteopiaUser,
    showContent: ShowContent,
    toggleMaxScreen: () -> Unit,
) {
    if (showContent == ShowContent.HIDE) return
    val isFull = showContent == ShowContent.FULL

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isFull) Arrangement.Start else Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onDoubleClick = toggleMaxScreen,
                onClick = {}
            )
            .padding(
                horizontal = if (isFull) 16.dp else 0.dp,
                vertical = 16.dp,
            )
    ) {
        UserAvatar(name = user.name, size = if (isFull) 40.dp else 36.dp)
        AnimatedVisibility(
            visible = isFull,
            enter = fadeIn(tween(LABEL_ANIM_DURATION)) + expandHorizontally(tween(LABEL_ANIM_DURATION)),
            exit = fadeOut(tween(LABEL_ANIM_DURATION)) + shrinkHorizontally(tween(LABEL_ANIM_DURATION)),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = user.tier.tierName(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = user.name.ifEmpty { user.email },
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
private fun UserAvatar(name: String, size: Dp) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = name.firstOrNull()?.uppercaseChar()?.toString() ?: "W",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

// ─── Section headers ─────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(text: String, showContent: ShowContent) {
    if (showContent == ShowContent.HIDE) return

    AnimatedVisibility(
        visible = showContent == ShowContent.FULL,
        enter = fadeIn(tween(LABEL_ANIM_DURATION)) + expandVertically(tween(LABEL_ANIM_DURATION)),
        exit = fadeOut(tween(LABEL_ANIM_DURATION)) + shrinkVertically(tween(LABEL_ANIM_DURATION)),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 12.dp,
                bottom = 4.dp,
            )
        )
    }
}

@Composable
private fun FolderSectionHeader(
    highlightContent: () -> Unit,
    addFolder: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, end = 6.dp, top = 8.dp, bottom = 4.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = WrStrings.folder(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = WrIcons.target,
            contentDescription = "Select opened file",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = highlightContent)
                .padding(4.dp)
        )
        Icon(
            imageVector = WrIcons.addCircle,
            contentDescription = "Add Folder",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp)
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = addFolder)
                .padding(4.dp)
        )
    }
}

// ─── Nav item ────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SideNavItem(
    showContent: ShowContent,
    iconVector: ImageVector?,
    contentDescription: String,
    text: String,
    selected: Boolean = false,
    click: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (showContent == ShowContent.HIDE) return
    val isFull = showContent == ShowContent.FULL

    @Composable
    fun ItemRow() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isFull) Arrangement.Start else Arrangement.Center,
            modifier = modifier
                .then(if (isFull) Modifier.fillMaxWidth() else Modifier.size(44.dp))
                .clip(MaterialTheme.shapes.small)
                .background(
                    if (selected) MaterialTheme.colorScheme.secondaryContainer
                    else Color.Transparent
                )
                .clickable(onClick = click)
                .padding(
                    horizontal = if (isFull) 16.dp else 0.dp,
                    vertical = 10.dp,
                )
        ) {
            iconVector?.let {
                Icon(
                    imageVector = it,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(20.dp),
                    tint = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            AnimatedVisibility(
                visible = isFull,
                enter = fadeIn(tween(LABEL_ANIM_DURATION)) + expandHorizontally(tween(LABEL_ANIM_DURATION)),
                exit = fadeOut(tween(LABEL_ANIM_DURATION)) + shrinkHorizontally(tween(LABEL_ANIM_DURATION)),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selected) MaterialTheme.colorScheme.onSecondaryContainer
                        else MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }

    if (!isFull) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
            tooltip = { PlainTooltip { Text(text) } },
            state = rememberTooltipState(),
        ) {
            ItemRow()
        }
    } else {
        ItemRow()
    }
}

// ─── Bottom actions ──────────────────────────────────────────────────────────

@Composable
private fun BottomActions(
    showContent: ShowContent,
    helpClick: () -> Unit,
    logoutClick: () -> Unit,
) {
    if (showContent == ShowContent.HIDE) return

    Column(
        horizontalAlignment = if (showContent == ShowContent.FULL) Alignment.Start
        else Alignment.CenterHorizontally,
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        BottomActionItem(
            showContent = showContent,
            icon = WrIcons.help,
            contentDescription = "Help",
            text = "Help",
            click = helpClick,
            modifier = Modifier.semantics { testTag = "sideMenuHelp" },
        )
        BottomActionItem(
            showContent = showContent,
            icon = WrIcons.logout,
            contentDescription = WrStrings.logout(),
            text = WrStrings.logout(),
            click = logoutClick,
            accentColor = MaterialTheme.colorScheme.error,
            boldText = true,
            modifier = Modifier.semantics { testTag = "sideMenuLogout" },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomActionItem(
    showContent: ShowContent,
    icon: ImageVector,
    contentDescription: String,
    text: String,
    click: () -> Unit,
    accentColor: Color? = null,
    boldText: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isFull = showContent == ShowContent.FULL
    val iconTint = accentColor ?: MaterialTheme.colorScheme.onSurfaceVariant
    val textColor = accentColor ?: MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun ItemRow() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isFull) Arrangement.Start else Arrangement.Center,
            modifier = modifier
                .then(if (isFull) Modifier.fillMaxWidth() else Modifier.size(44.dp))
                .clickable(onClick = click)
                .padding(
                    horizontal = if (isFull) 16.dp else 0.dp,
                    vertical = 12.dp,
                )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(20.dp),
            )
            AnimatedVisibility(
                visible = isFull,
                enter = fadeIn(tween(LABEL_ANIM_DURATION)) + expandHorizontally(tween(LABEL_ANIM_DURATION)),
                exit = fadeOut(tween(LABEL_ANIM_DURATION)) + shrinkHorizontally(tween(LABEL_ANIM_DURATION)),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = text,
                        style = if (boldText) MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        else MaterialTheme.typography.bodySmall,
                        color = textColor,
                    )
                }
            }
        }
    }

    if (!isFull) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
            tooltip = { PlainTooltip { Text(text) } },
            state = rememberTooltipState(),
        ) {
            ItemRow()
        }
    } else {
        ItemRow()
    }
}

// ─── Folder section title ────────────────────────────────────────────────────

@Composable
private fun title(
    text: String,
    click: (() -> Unit)? = null,
    trailingContent: @Composable (RowScope.() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.let { m ->
            if (click != null) m.clickable(onClick = click) else m
        }
            .padding(start = 16.dp, end = 6.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        trailingContent?.invoke(this)
    }
}

@Preview
@Composable
fun SideGlobalMenuPreview() {
    SideGlobalMenu(
        modifier = Modifier.background(Color.Cyan),
        foldersState = MutableStateFlow(emptyList()),
        userState = MutableStateFlow(WriteopiaUser.disconnectedUser()),
        searchClick = {},
        homeClick = {},
        favoritesClick = {},
        forceGraphClick = {},
        trashClick = {},
        settingsClick = {},
        addFolder = {},
        highlightContent = {},
        editFolder = {},
        navigateToFolder = {},
        navigateToEditDocument = { _, _ -> },
        moveRequest = { _, _ -> },
        expandFolder = {},
        changeIcon = { _, _, _, _ -> },
        toggleMaxScreen = {},
        helpClick = {},
        logoutClick = {},
    )
}

private enum class ShowContent {
    HIDE,
    ICONS,
    FULL
}
