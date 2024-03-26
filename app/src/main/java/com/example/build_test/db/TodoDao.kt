package com.example.build_test.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun create(todo: TodoEntity): Long

    @Query("SELECT * FROM todo order by updatedAt desc")
    fun getAll(): Flow<List<TodoEntity>>

    @Update
    suspend fun update(entity: TodoEntity)

    @Query("SELECT * FROM todo WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): TodoEntity?
}