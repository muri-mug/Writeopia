package io.writeopia.notes.desktop.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.writeopia.common.utils.icons.PlatformIcons
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalHeader(
    navigationController: NavHostController,
    pathState: StateFlow<List<String>>,
    toggleMaxScreen: () -> Unit,
) {
    TopAppBar(
        title = { PathToCurrentDirectory(pathState) },
        navigationIcon = {
            IconButton(
                onClick = {
                    if (navigationController.previousBackStackEntry != null) {
                        navigationController.navigateUp()
                    }
                }
            ) {
                Icon(
                    imageVector = PlatformIcons.backArrowMobile,
                    contentDescription = "Navigate back",
                )
            }
        },
        modifier = Modifier.combinedClickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onDoubleClick = toggleMaxScreen,
            onClick = {},
        ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
    )
}

@Composable
private fun PathToCurrentDirectory(pathState: StateFlow<List<String>>) {
    val path by pathState.collectAsState()
    val size = path.lastIndex

    Row(verticalAlignment = Alignment.CenterVertically) {
        path.forEachIndexed { i, nodePath ->
            Text(
                text = nodePath,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            if (i != size) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
