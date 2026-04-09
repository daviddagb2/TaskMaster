package com.dgb.taskmaster

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dgb.taskmaster.data.ThemePreferencesStore
import com.dgb.taskmaster.data.local.database.AppDatabase
import com.dgb.taskmaster.data.repository.TaskRepositoryImpl
import com.dgb.taskmaster.domain.model.ThemeMode
import com.dgb.taskmaster.presentation.settings.SettingsScreen
import com.dgb.taskmaster.presentation.settings.SettingsViewModel
import com.dgb.taskmaster.presentation.settings.SettingsViewModelFactory
import com.dgb.taskmaster.presentation.tasklist.TaskListScreen
import com.dgb.taskmaster.presentation.tasklist.TaskViewModel
import com.dgb.taskmaster.presentation.tasklist.TaskViewModelFactory
import com.dgb.taskmaster.ui.theme.TaskMasterTheme


private const val ROUTE_TASKS = "tasks"
private const val ROUTE_SETTINGS = "settings"

@Composable
fun TaskMasterApp() {

    val context = LocalContext.current
    val themeStore = remember { ThemePreferencesStore(context) }
    val settingsFactory = remember { SettingsViewModelFactory(themeStore) }
    val settingsViewModel: SettingsViewModel = viewModel(factory = settingsFactory)
    val systemDark = isSystemInDarkTheme()
    val themeMode by settingsViewModel.themeMode.collectAsStateWithLifecycle()
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> systemDark
    }


    TaskMasterTheme(darkTheme = darkTheme){
        val taskFactory = remember {
            val db = AppDatabase.getInstance(context.applicationContext)
            TaskViewModelFactory(TaskRepositoryImpl(db.taskDato()))
        }
        val taskViewModel: TaskViewModel = viewModel(factory = taskFactory)

        var route by rememberSaveable { mutableStateOf(ROUTE_TASKS) }

        when (route) {
            ROUTE_TASKS -> {
                TaskListScreen(
                    viewModel = taskViewModel,
                    onOpenSettings = { route = ROUTE_SETTINGS },
                    modifier = Modifier.fillMaxSize(),
                )
            }
            ROUTE_SETTINGS -> {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onNavigateBack = { route = ROUTE_TASKS },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
