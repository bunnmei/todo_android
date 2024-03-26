package com.example.build_test.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class LocalTodoRepository @Inject constructor(
    private val toDoDao: TodoDao,
): TodoRepository {
    override suspend fun create(title: String, description: String): Todo {
        val todo = TodoEntity (
            id = 0,
            title = title,
            description = description,
            done = 0,
            createAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
        )
        val id = toDoDao.create(todo)
        return Todo(
            id = id,
            title = title,
            description = description,
            isDone = false,
            createAt = Date(todo.createAt),
            updatedAt = Date(todo.updatedAt)
        )
    }

    override fun getAll(): Flow<List<Todo>> {
        return toDoDao.getAll().map { items ->
            items.map { item -> item.toModel() }
        }
    }

    override suspend fun update(todo: Todo) {
        val entity = TodoEntity(
            id = todo.id,
            title = todo.title,
            description = todo.description,
            done = if(todo.isDone) 1 else 0,
            createAt = todo.createAt.time,
            updatedAt = System.currentTimeMillis(),
        )
        toDoDao.update(entity)
    }

    override suspend fun getById(id: Long): Todo? {
        val entity = toDoDao.getById(id)
        return entity?.toModel()
    }
}

private fun TodoEntity.toModel() = Todo(
    id = id,
    title = title,
    description = description,
    isDone = done == 1,
    createAt = Date(createAt),
    updatedAt = Date(updatedAt),
)