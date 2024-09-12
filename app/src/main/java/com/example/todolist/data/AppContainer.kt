package com.example.todolist.data

import android.content.Context

interface AppContainer {
    val toDoItemsRepository: ToDoItemsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val toDoItemsRepository: ToDoItemsRepository by lazy {
        OfflineToDoItemsRepository(ToDoListDatabase.getDatabase(context).taskDao())
    }
}
