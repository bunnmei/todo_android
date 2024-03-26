package com.example.build_test.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TodoEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun TodoDao() : TodoDao
}