package com.example.build_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.build_test.db.LocalTodoRepository
import com.example.build_test.db.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CreateViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    sealed interface UiState {
        data object Idle: UiState
        data object InputError: UiState
        data object Success: UiState
        data class CreateError(val e:Exception): UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun create(title: String, description: String){
        if (title.isEmpty()){
            _uiState.value = UiState.InputError
            return
        }
        viewModelScope.launch {
            try {
                todoRepository.create(title, description)
                _uiState.value = UiState.Success
            } catch(e: Exception) {
                _uiState.value = UiState.CreateError(e)
            }

        }
    }

    fun moveToIdle() {
        _uiState.value = UiState.Idle
    }
}