package com.example.todoapp.database.dao

import androidx.room.*
import com.example.todoapp.database.model.Tasks

@Dao
interface TasksDao {
    @Insert
    fun addTask(tasks: Tasks)

    @Delete
    fun deleteTask(Task: Tasks)

    @Query("UPDATE Tasks SET isDone =:isDone WHERE id = :id")
    fun updateTask(isDone: Boolean, id: Int)

    @Query("SELECT * FROM Tasks")
    fun getTasks(): List<Tasks>

    @Query("select * from Tasks where date =:date ")
    fun getTasksByDate(date: Long): List<Tasks>
}