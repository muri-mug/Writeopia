@file:OptIn(ExperimentalTime::class)

package io.writeopia.notemenu.ui.screen.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.writeopia.sdk.models.document.Folder
import kotlin.time.ExperimentalTime

@Composable
fun EditFileDialog(
    folderEdit: Folder,
    onDismissRequest: () -> Unit,
    editFolder: (Folder) -> Unit,
    deleteFolder: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var fileText by remember { mutableStateOf(folderEdit.title) }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = { Text("Update Folder") },
        text = {
            OutlinedTextField(
                value = fileText,
                onValueChange = { title ->
                    fileText = title
                    editFolder(folderEdit.copy(title = title.takeIf { it.isNotEmpty() } ?: " "))
                },
                label = { Text("Folder name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Done")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    deleteFolder(folderEdit.id)
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error,
                ),
            ) {
                Text("Delete folder")
            }
        },
    )
}
