package com.example.todolist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {
    @Query("SELECT * from todo_table WHERE id = :id")
    fun getTask(id: Int): Flow<ToDoItem>

    @Query("SELECT * from todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(toDoItem: ToDoItem)

    @Delete
    suspend fun deleteTask(toDoItem: ToDoItem)

    @Update
    suspend fun updateTask(toDoItem: ToDoItem)
}