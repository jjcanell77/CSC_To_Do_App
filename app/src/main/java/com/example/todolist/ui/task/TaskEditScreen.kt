package com.example.todolist.ui.task

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.R
import com.example.todolist.ToDoListTopAppBar
import com.example.todolist.ui.AppViewModelProvider
import com.example.todolist.ui.navigation.NavigationDestination
import com.example.todolist.ui.theme.ToDoListTheme
import kotlinx.coroutines.launch

object TaskEditDestination : NavigationDestination {
    override val route = "task_edit"
    override val titleRes = R.string.edit_task_title
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val isEdit = true;
    Scaffold(
        topBar = {
            ToDoListTopAppBar(
                title = stringResource(R.string.edit_task_title),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
            TaskEntryBody(
                taskUiState = viewModel.taskUiState,
                onTaskValueChange = viewModel::updateUiState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateTask()
                        navigateBack()
                    }
                },
                onDelete = {
                    coroutineScope.launch {
                        viewModel.deleteItem()
                        navigateBack()
                    }
                },
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                        top = innerPadding.calculateTopPadding(),
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    )
                    .verticalScroll(rememberScrollState()),
                isEdit = isEdit
            )
        }
    }


@Preview(showBackground = true)
@Composable
fun TaskEditScreenPreview() {
    ToDoListTheme {
        TaskEntryBody(
            taskUiState = TaskUiState(
                taskDetails = TaskDetails(
                    id = 1,
                    task = "Do Homework",
                    isComplete = true
                )
            ),
            onTaskValueChange = {},
            onSaveClick = { },
            onDelete = {},
            isEdit = true
        )    }
}