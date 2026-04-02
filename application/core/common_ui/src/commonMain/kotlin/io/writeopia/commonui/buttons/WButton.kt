package io.writeopia.commonui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class WButtonVariant {
    /** Filled — use for the single most important action on screen. */
    Primary,

    /** Muted fill — use for secondary/supporting actions. */
    Secondary,

    /** No background — use for low-emphasis / tertiary actions. */
    Ghost,
}

/**
 * Writeopia button following shadcn/ui default button styles, mapped to the
 * project's Material 3 colour tokens.
 *
 * Variants:
 *  - [WButtonVariant.Primary]   → primary bg, onPrimary text
 *  - [WButtonVariant.Secondary] → secondaryContainer bg, onSecondaryContainer text
 *  - [WButtonVariant.Ghost]     → transparent bg, onBackground text
 */
@Composable
fun WButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: WButtonVariant = WButtonVariant.Primary,
    leadingIcon: ImageVector? = null,
    leadingIconDescription: String? = null,
    enabled: Boolean = true,
) {
    val colors = MaterialTheme.colorScheme
    val shape = MaterialTheme.shapes.medium   // 6 dp rounded corners

    val containerColor: Color
    val contentColor: Color
    val borderStroke: BorderStroke?

    when (variant) {
        WButtonVariant.Primary -> {
            containerColor = if (enabled) colors.primary else colors.onSurface.copy(alpha = 0.12f)
            contentColor = if (enabled) colors.onPrimary else colors.onSurface.copy(alpha = 0.38f)
            borderStroke = null
        }

        WButtonVariant.Secondary -> {
            containerColor = if (enabled) colors.secondaryContainer else colors.onSurface.copy(alpha = 0.12f)
            contentColor = if (enabled) colors.onSecondaryContainer else colors.onSurface.copy(alpha = 0.38f)
            borderStroke = null
        }

        WButtonVariant.Ghost -> {
            containerColor = Color.Transparent
            contentColor = if (enabled) colors.onBackground else colors.onSurface.copy(alpha = 0.38f)
            borderStroke = null
        }
    }

    var rowModifier = modifier
        .height(36.dp)
        .clip(shape)
        .background(containerColor, shape)

    if (borderStroke != null) {
        rowModifier = rowModifier.border(borderStroke, shape)
    }

    if (enabled) {
        rowModifier = rowModifier.clickable(onClick = onClick)
    }

    rowModifier = rowModifier.padding(horizontal = 16.dp)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = rowModifier,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadingIconDescription,
                tint = contentColor,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = contentColor,
        )
    }
}
