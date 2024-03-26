package com.example.build_test.db

import java.util.Date

data class Todo(
    val id:Long,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val createAt: Date,
    val updatedAt: Date,
)
