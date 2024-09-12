package com.example.todolist.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todolist.ToDoApplication
import com.example.todolist.ui.task.TaskHomeViewModel
import com.example.todolist.ui.task.TaskEditViewModel
import com.example.todolist.ui.task.TaskEntryViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TaskEditViewModel(
                this.createSavedStateHandle(),
                toDoApplication().container.toDoItemsRepository
            )
        }
        initializer {
            TaskEntryViewModel(toDoApplication().container.toDoItemsRepository)
        }
        initializer {
            TaskHomeViewModel(toDoApplication().container.toDoItemsRepository)
        }
    }
}

fun CreationExtras.toDoApplication(): ToDoApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ToDoApplication)
