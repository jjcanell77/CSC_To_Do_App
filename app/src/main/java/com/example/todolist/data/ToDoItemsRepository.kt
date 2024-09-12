package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

interface ToDoItemsRepository{

    fun getAllTasksStream(): Flow<List<ToDoItem>>

    fun getTaskStream(id: Int): Flow<ToDoItem?>

    suspend fun insertTask(task: ToDoItem)

    suspend fun deleteTask(task: ToDoItem)

    suspend fun updateTask(task: ToDoItem)
}
