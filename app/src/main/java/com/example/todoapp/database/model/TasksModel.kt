package com.example.todoapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo var title: String,
    @ColumnInfo var description: String,
    @ColumnInfo var date: Long,
    @ColumnInfo var isDone: Boolean = false,
)