package com.example.build_test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.build_test.db.Todo
import com.example.build_test.db.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val id: Long = savedStateHandle.get<Long>("todoId")?: throw  IllegalArgumentException("id is required")

    sealed interface UiState {
        data object Initial: UiState
        data object Loading: UiState
        data class LoadSuccess(val todo: Todo): UiState
        data class LoadError(val error: Throwable): UiState
        data object Idle: UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun load() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val todo = todoRepository.getById(id)
                if (todo == null) {
                    _uiState.value = UiState.LoadError(IllegalArgumentException("id is not found"))
                    return@launch
                }
                _uiState.value = UiState.LoadSuccess(todo)
            } catch (e: Exception) {
                _uiState.value = UiState.LoadError(e)
            }
        }
    }

    fun moveToIdle() {
        _uiState.value = UiState.Idle
    }
}
