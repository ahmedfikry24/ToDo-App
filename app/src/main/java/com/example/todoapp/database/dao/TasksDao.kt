package com.example.todoapp.database.dao

import androidx.room.*
import com.example.todoapp.database.model.Tasks

@Dao
interface TasksDao {
    @Insert
    fun addTask(tasks: Tasks)

    @Delete
    fun deleteTask(tasks: Tasks)

    @Update
    fun updateTask(tasks: Tasks)

    @Query("select * from Tasks")
    fun getTasks()
}