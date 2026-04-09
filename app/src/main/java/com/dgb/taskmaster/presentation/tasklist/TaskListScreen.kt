package com.dgb.taskmaster.presentation.tasklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dgb.taskmaster.R
import com.dgb.taskmaster.domain.model.Task
import com.dgb.taskmaster.presentation.components.TaskDialog
import com.dgb.taskmaster.presentation.components.TaskItemCard
import com.dgb.taskmaster.ui.theme.TaskMasterTheme

private sealed class EditorState {
    data object Closed : EditorState()
    data object Add : EditorState()
    data class Edit(val task: Task) : EditorState()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
){
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    var editorState by remember { mutableStateOf<EditorState>(EditorState.Closed) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Task Master",
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings_open_cd),
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { editorState = EditorState.Add },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 6.dp,
                ),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar tarea",
                )
            }
        }
    ){ innerPadding ->

        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No hay tareas aún.\nToca + para crear la primera.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              items(
                  items = tasks,
                  key = {it.id}
              ){ task ->
                  TaskItemCard(
                      task = task,
                      onToggleCompleted = { viewModel.toggleTaskCompleted(task) },
                      onEdit = { editorState = EditorState.Edit(task) },
                      onDelete = { viewModel.deleteTask(task) },
                  )
              }
            }
        }
    }

    when (val state = editorState) {
        EditorState.Closed -> Unit
        EditorState.Add -> {
            TaskDialog(
                dialogTitle = "Nueva tarea",
                initialTitle = "",
                initialDescription = "",
                onDismiss = { editorState = EditorState.Closed },
                onSave = { title, description ->
                    viewModel.addTask(title, description)
                    editorState = EditorState.Closed
                },
            )
        }
        is EditorState.Edit -> {
            TaskDialog(
                dialogTitle = "Editar tarea",
                initialTitle = state.task.title,
                initialDescription = state.task.description,
                onDismiss = { editorState = EditorState.Closed },
                onSave = { title, description ->
                    viewModel.updateTask(
                        state.task.copy(
                            title = title,
                            description = description,
                        ),
                    )
                    editorState = EditorState.Closed
                },
            )
        }
    }
}

@Preview
@Composable
fun TaskListEmptyScreen(){
    TaskMasterTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Vista previa: conecta TaskViewModel en la app",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}