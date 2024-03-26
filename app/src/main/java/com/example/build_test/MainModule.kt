package com.example.build_test

import android.content.Context
import androidx.room.Room
import com.example.build_test.db.LocalTodoRepository
import com.example.build_test.db.TodoDao
import com.example.build_test.db.TodoDatabase
import com.example.build_test.db.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun bindToDoRepository(impl: LocalTodoRepository): TodoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo.db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoDao(db: TodoDatabase): TodoDao {
        return db.TodoDao()
    }

}