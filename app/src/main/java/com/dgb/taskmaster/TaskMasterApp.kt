package com.dgb.taskmaster

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dgb.taskmaster.data.local.database.AppDatabase
import com.dgb.taskmaster.data.repository.TaskRepositoryImpl
import com.dgb.taskmaster.presentation.tasklist.TaskListScreen
import com.dgb.taskmaster.presentation.tasklist.TaskViewModel
import com.dgb.taskmaster.presentation.tasklist.TaskViewModelFactory
import com.dgb.taskmaster.ui.theme.TaskMasterTheme

@Composable
fun TaskMasterApp() {
    TaskMasterTheme {
        val context = LocalContext.current
        val factory = remember {
            val db = AppDatabase.getInstance(context.applicationContext)
            TaskViewModelFactory(TaskRepositoryImpl(db.taskDato()))
        }
        val viewModel: TaskViewModel = viewModel(factory = factory)

        TaskListScreen(
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
