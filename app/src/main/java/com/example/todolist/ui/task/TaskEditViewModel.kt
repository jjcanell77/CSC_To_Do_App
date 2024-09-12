package com.example.todolist.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDoItemsRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskEditViewModel (
    savedStateHandle: SavedStateHandle,
    private val toDoItemsRepository: ToDoItemsRepository
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    private val taskId: Int = checkNotNull(savedStateHandle[TaskEditDestination.taskIdArg])

    init {
        viewModelScope.launch {
            taskUiState = toDoItemsRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState(true)
        }
    }

    suspend fun deleteItem() {
        toDoItemsRepository.deleteTask(taskUiState.taskDetails.toTask())
    }


    suspend fun updateTask() {
        if (validateInput(taskUiState.taskDetails)) {
            toDoItemsRepository.updateTask(taskUiState.taskDetails.toTask())
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(
                taskDetails = taskDetails,
                isEntryValid = validateInput(taskDetails))
    }

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            task.isNotBlank()
        }
    }
}