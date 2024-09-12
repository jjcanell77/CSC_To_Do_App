package com.example.todolist.ui.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.R
import com.example.todolist.ToDoListTopAppBar
import com.example.todolist.data.ToDoItem
import com.example.todolist.ui.AppViewModelProvider
import com.example.todolist.ui.navigation.NavigationDestination
import com.example.todolist.ui.theme.ToDoListTheme
import androidx.compose.ui.res.dimensionResource as dimensionResource1

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskHomeScreen(
    navigateToTaskEntry: () -> Unit,
    navigateToTaskUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TaskHomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ToDoListTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToTaskEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource1(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            taskList = homeUiState.taskList,
            onTaskClick = navigateToTaskUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun HomeBody(
    taskList: List<ToDoItem>,
    onTaskClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (taskList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_task_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            TaskList(
                taskList = taskList,
                onTaskClick = { onTaskClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource1(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun TaskList(
    taskList: List<ToDoItem>,
    onTaskClick: (ToDoItem) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = taskList, key = { it.id }) { task ->
            TaskDetails(task = task,
                modifier = Modifier
                    .padding(dimensionResource1(id = R.dimen.padding_small))
                    .clickable { onTaskClick(task) })
        }
    }
}

@Composable
fun TaskDetails(
    task: ToDoItem, modifier: Modifier = Modifier
) {
    val box = if(task.isComplete) painterResource(R.drawable.checked) else painterResource(R.drawable.unchecked)
    val contentDescription = if (task.isComplete) stringResource(R.string.task_complete) else stringResource(R.string.task_not_completed)
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row( modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.padding_medium)
            )) {
                Text(
                    text = stringResource(R.string.home_task),
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = task.task,
                    fontWeight = FontWeight.Bold
                )
            }
            Row( modifier = Modifier.padding(
                horizontal = dimensionResource(
                    id = R.dimen.padding_medium)
            )) {
                Text(
                    text = stringResource(R.string.home_is_complete),
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Image(
                    painter = box,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    ToDoListTheme {
        HomeBody(listOf(
            ToDoItem(1, "Finish Homework", true),
            ToDoItem(2, "Take Out Trash", false),
            ToDoItem(3, "Feed Dog", true)),
            onTaskClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    ToDoListTheme {
        HomeBody(listOf(), onTaskClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    ToDoListTheme {
        TaskDetails(
            ToDoItem(1, "Task Goes Here", true)
        )
    }
}