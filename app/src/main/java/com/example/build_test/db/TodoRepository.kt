package com.example.build_test.db

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun create(title: String, description: String): Todo
    fun getAll(): Flow<List<Todo>>
    suspend fun update(todo: Todo)
    suspend fun getById(id: Long): Todo?
}