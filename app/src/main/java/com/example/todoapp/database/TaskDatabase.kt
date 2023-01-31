package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.TasksDao
import com.example.todoapp.database.model.Tasks

@Database(
    entities = [
        Tasks::class
    ], version = 1, exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private var taskDatabase: TaskDatabase? = null
        fun createDatabase(context: Context): TaskDatabase {
            if (taskDatabase == null) {
                taskDatabase = Room.databaseBuilder(context, TaskDatabase::class.java, "Tasks_Name")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return taskDatabase!!
        }
    }
}
