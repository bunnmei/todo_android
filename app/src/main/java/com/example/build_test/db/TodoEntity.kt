package com.example.build_test.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val done: Int,
    val createAt: Long,
    val updatedAt: Long,
)
