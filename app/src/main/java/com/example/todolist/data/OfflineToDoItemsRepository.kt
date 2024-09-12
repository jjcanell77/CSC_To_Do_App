package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

class OfflineToDoItemsRepository(private val itemDao: ToDoItemDao) : ToDoItemsRepository {
    override fun getAllTasksStream(): Flow<List<ToDoItem>> = itemDao.getAllTasks()

    override fun getTaskStream(id: Int): Flow<ToDoItem?> = itemDao.getTask(id)

    override suspend fun insertTask(task: ToDoItem) = itemDao.addTask(task)

    override suspend fun deleteTask(task: ToDoItem) = itemDao.deleteTask(task)

    override suspend fun updateTask(task: ToDoItem) = itemDao.updateTask(task)
}