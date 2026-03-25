package io.writeopia.global.shell

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.writeopia.common.utils.icons.WrIcons

data class PaletteCommand(
    val label: String,
    val icon: ImageVector,
    val action: () -> Unit,
)

@Composable
fun CommandPaletteDialog(
    onDismissRequest: () -> Unit,
    onSearchClick: () -> Unit,
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onNotesMapClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onTrashClick: () -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var selectedIndex by remember { mutableStateOf(0) }

    val allCommands = remember(
        onSearchClick, onHomeClick, onFavoritesClick,
        onNotesMapClick, onSettingsClick, onTrashClick
    ) {
        listOf(
            PaletteCommand("Search notes", WrIcons.search) { onSearchClick(); onDismissRequest() },
            PaletteCommand("Home", WrIcons.home) { onHomeClick(); onDismissRequest() },
            PaletteCommand("Favorites", WrIcons.favorites) { onFavoritesClick(); onDismissRequest() },
            // TODO: Notes map - hidden, to be restored in the future
//            PaletteCommand("Notes map", WrIcons.chart) { onNotesMapClick(); onDismissRequest() },
            PaletteCommand("Trash", WrIcons.delete) { onTrashClick(); onDismissRequest() },
            PaletteCommand("Settings", WrIcons.settings) { onSettingsClick(); onDismissRequest() },
        )
    }

    val filtered = remember(query, allCommands) {
        if (query.isBlank()) allCommands
        else allCommands.filter { it.label.contains(query, ignoreCase = true) }
    }

    // Clamp selectedIndex when filtered list shrinks
    val clampedIndex = if (filtered.isEmpty()) 0 else selectedIndex.coerceIn(0, filtered.lastIndex)

    val focusRequester = remember { FocusRequester() }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.width(600.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column {
                // Search input
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = WrIcons.search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .onKeyEvent { keyEvent ->
                                    when {
                                        keyEvent.key == Key.DirectionDown &&
                                                keyEvent.type == KeyEventType.KeyDown -> {
                                            selectedIndex = (clampedIndex + 1)
                                                .coerceAtMost(filtered.lastIndex.coerceAtLeast(0))
                                            true
                                        }
                                        keyEvent.key == Key.DirectionUp &&
                                                keyEvent.type == KeyEventType.KeyDown -> {
                                            selectedIndex = (clampedIndex - 1).coerceAtLeast(0)
                                            true
                                        }
                                        keyEvent.key == Key.Enter &&
                                                keyEvent.type == KeyEventType.KeyUp -> {
                                            filtered.getOrNull(clampedIndex)?.action?.invoke()
                                            true
                                        }
                                        keyEvent.key == Key.Escape &&
                                                keyEvent.type == KeyEventType.KeyUp -> {
                                            onDismissRequest()
                                            true
                                        }
                                        else -> false
                                    }
                                },
                            value = query,
                            onValueChange = {
                                query = it
                                selectedIndex = 0
                            },
                            singleLine = true,
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground)
                        )

                        if (query.isEmpty()) {
                            Text(
                                "Type a command…",
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    AnimatedVisibility(query.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                        Icon(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { query = ""; selectedIndex = 0 },
                            imageVector = WrIcons.close,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                HorizontalDivider()

                if (filtered.isEmpty()) {
                    Text(
                        "No commands found",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
                        itemsIndexed(filtered) { index, command ->
                            val isSelected = index == clampedIndex
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                                        else androidx.compose.ui.graphics.Color.Transparent
                                    )
                                    .clickable { command.action() }
                                    .padding(horizontal = 12.dp, vertical = 10.dp)
                            ) {
                                Icon(
                                    imageVector = command.icon,
                                    contentDescription = command.label,
                                    modifier = Modifier.size(18.dp),
                                    tint = if (isSelected)
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    else
                                        MaterialTheme.colorScheme.onBackground
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = command.label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.onSecondaryContainer
                                    else
                                        MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
