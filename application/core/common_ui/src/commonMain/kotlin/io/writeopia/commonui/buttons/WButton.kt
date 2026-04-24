package io.writeopia.commonui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// M3 button roles — https://m3.material.io/components/buttons/overview
enum class WButtonVariant {
    /** M3 Filled button — highest emphasis. */
    Filled,

    /** M3 Filled Tonal button — medium-high emphasis. */
    Tonal,

    /** M3 Outlined button — medium emphasis. */
    Outlined,

    /** M3 Text button — lowest emphasis. */
    Text,

    /** Destructive action — error color scheme. */
    Destructive,
}

@Composable
fun WButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: WButtonVariant = WButtonVariant.Filled,
    leadingIcon: ImageVector? = null,
    leadingIconDescription: String? = null,
    enabled: Boolean = true,
) {
    val icon: @Composable (() -> Unit)? = if (leadingIcon != null) {
        {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDescription,
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
        }
    } else null

    when (variant) {
        WButtonVariant.Filled -> Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
        ) {
            if (icon != null) {
                icon()
                Text(text = text, fontWeight = FontWeight.Medium)
            } else {
                Text(text = text, fontWeight = FontWeight.Medium)
            }
        }

        WButtonVariant.Tonal -> FilledTonalButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
        ) {
            if (icon != null) {
                icon()
                Text(text = text, fontWeight = FontWeight.Medium)
            } else {
                Text(text = text, fontWeight = FontWeight.Medium)
            }
        }

        WButtonVariant.Outlined -> OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
        ) {
            if (icon != null) {
                icon()
                Text(text = text, fontWeight = FontWeight.Medium)
            } else {
                Text(text = text, fontWeight = FontWeight.Medium)
            }
        }

        WButtonVariant.Text -> TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
        ) {
            if (icon != null) {
                icon()
                Text(text = text, fontWeight = FontWeight.Medium)
            } else {
                Text(text = text, fontWeight = FontWeight.Medium)
            }
        }

        WButtonVariant.Destructive -> Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor   = MaterialTheme.colorScheme.onError,
            ),
            border = null,
        ) {
            if (icon != null) {
                icon()
                Text(text = text, fontWeight = FontWeight.Medium)
            } else {
                Text(text = text, fontWeight = FontWeight.Medium)
            }
        }
    }
}
