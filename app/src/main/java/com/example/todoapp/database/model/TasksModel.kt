package com.example.todoapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val date: Long,
    @ColumnInfo var isDone: Boolean = false,
)