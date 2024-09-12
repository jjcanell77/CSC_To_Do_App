package com.example.todolist.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todolist.data.ToDoItem
import com.example.todolist.data.ToDoItemsRepository

class TaskEntryViewModel(private val toDoItemsRepository: ToDoItemsRepository) : ViewModel() {

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            toDoItemsRepository.insertTask(taskUiState.taskDetails.toTask())
        }
    }

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            task.isNotBlank()
        }
    }
}

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)

data class TaskDetails(
    val id: Int = 0,
    val task: String = "",
    val isComplete: Boolean = false,
)

fun TaskDetails.toTask(): ToDoItem = ToDoItem(
    id = id,
    task = task,
    isComplete = isComplete
)

fun ToDoItem.toTaskUiState(isEntryValid: Boolean = false): TaskUiState = TaskUiState(
    taskDetails = this.toTaskDetails(),
    isEntryValid = isEntryValid
)

fun ToDoItem.toTaskDetails(): TaskDetails = TaskDetails(
    id = id,
    task = task,
    isComplete = isComplete
)