package com.example.todoapp.database.dao

import androidx.room.*
import com.example.todoapp.database.model.Tasks

@Dao
interface TasksDao {
    @Insert
    fun addTask(tasks: Tasks)

    @Delete
    fun deleteTask(Task: Tasks)

    @Update
    fun updateTask(tasks: Tasks)

    @Query("SELECT * FROM Tasks")
    fun getTasks(): List<Tasks>

    @Query("select * from Tasks where date =:date ")
    fun getTasksByDate(date: Long): List<Tasks>
}