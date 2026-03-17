package io.writeopia.common.utils.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.writeopia.common.utils.icons.WrIcons
import kotlinx.coroutines.delay

enum class SnackbarType { SUCCESS, ERROR, INFO, WARNING }

data class SnackbarMessage(
    val text: String,
    val type: SnackbarType = SnackbarType.INFO,
)

class ToastInfo {
    var hideGlobalContent: Boolean by mutableStateOf(false)
    var snackbarMessage: SnackbarMessage? by mutableStateOf(null)

    fun showSnackbar(text: String, type: SnackbarType = SnackbarType.INFO) {
        snackbarMessage = SnackbarMessage(text, type)
    }

    fun dismissSnackbar() {
        snackbarMessage = null
    }
}

@Composable
fun GlobalToastBox(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    val state by remember { mutableStateOf(ToastInfo()) }

    val newModifier = if (state.hideGlobalContent) modifier.blur(10.dp) else modifier

    CompositionLocalProvider(LocalToastInfo provides state) {
        Box(modifier = newModifier) {
            content()

            val snackbar = state.snackbarMessage
            AnimatedVisibility(
                visible = snackbar != null,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp),
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it },
            ) {
                if (snackbar != null) {
                    WrSnackbar(
                        message = snackbar,
                        onDismiss = state::dismissSnackbar,
                    )
                }
            }
        }
    }
}

@Composable
private fun WrSnackbar(
    message: SnackbarMessage,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(message) {
        delay(3500)
        onDismiss()
    }

    val backgroundColor = when (message.type) {
        SnackbarType.SUCCESS -> Color(0xFF2E7D32)
        SnackbarType.ERROR -> Color(0xFFC62828)
        SnackbarType.INFO -> Color(0xFF1565C0)
        SnackbarType.WARNING -> Color(0xFFE65100)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = message.text,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = WrIcons.close,
            contentDescription = "Dismiss notification",
            tint = Color.White,
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable(onClick = onDismiss),
        )
    }
}
