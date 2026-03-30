package com.dgb.taskmaster.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dgb.taskmaster.ui.theme.TaskMasterTheme

@Composable
fun TaskDialog(
    dialogTitle: String,
    initialTitle: String,
    initialDescription: String,
    onDismiss: () -> Unit,
    onSave: (title: String, description: String) -> Unit,
    modifier: Modifier = Modifier,
    saveButtonText: String = "Guardar",
    cancelButtonText: String = "Cancelar",
    ){

    var titleField by remember { mutableStateOf(initialTitle) }
    var descriptionField by remember { mutableStateOf(initialDescription) }

    LaunchedEffect(initialTitle, initialDescription) {
        titleField = initialTitle
        descriptionField = initialDescription
    }

    val titleValid = titleField.trim().isNotEmpty()

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = titleField,
                    onValueChange = { titleField = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Título") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    isError = !titleValid && titleField.isNotEmpty(),
                    supportingText = if (!titleValid && titleField.isNotEmpty()) {
                        { Text("El título no puede estar vacío") }
                    } else {
                        null
                    },
                )
                OutlinedTextField(
                    value = descriptionField,
                    onValueChange = { descriptionField = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Descripción") },
                    minLines = 2,
                    maxLines = 4,
                    shape = RoundedCornerShape(12.dp),
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (titleValid) {
                        onSave(titleField.trim(), descriptionField.trim())
                    }
                },
                enabled = titleValid,
            ) {
                Text(saveButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(cancelButtonText)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TaskDialogCreatePreview() {
    TaskMasterTheme {
        TaskDialog(
            dialogTitle = "Nueva tarea",
            initialTitle = "",
            initialDescription = "",
            onDismiss = {},
            onSave = { _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskDialogEditPreview() {
    TaskMasterTheme {
        TaskDialog(
            dialogTitle = "Editar tarea",
            initialTitle = "Comprar leche",
            initialDescription = "Deslactosada",
            onDismiss = {},
            onSave = { _, _ -> },
        )
    }
}