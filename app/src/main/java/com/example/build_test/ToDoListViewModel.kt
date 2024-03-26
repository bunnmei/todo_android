package com.example.build_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.build_test.db.Todo
import com.example.build_test.db.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {
    val items = repository.getAll()

    fun update(todo: Todo){
        viewModelScope.launch {
            try {
                repository.update(todo)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}