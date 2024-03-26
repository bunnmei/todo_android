package com.example.build_test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.internal.OpDescriptor


@Composable
fun EditToDoScreen(
    viewModel: EditViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold { pad ->
        when(uiState) {
            EditViewModel.UiState.Loading,
            EditViewModel.UiState.Idle -> {
                EditToDoFrom(
                    modifier = Modifier.padding(pad),
                    title = title,
                    description = description,
                    setTitle = { title = it},
                    setDescription = { description = it},
                )
            }
            EditViewModel.UiState.Initial,
            is EditViewModel.UiState.LoadError -> {
                Box(
                    modifier = Modifier
                        .padding(pad)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "Error")
                }
            }
            is EditViewModel.UiState.LoadSuccess -> {
                Box(
                    modifier = Modifier
                        .padding(pad)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                    ){
                    CircularProgressIndicator()
                }
            }
        }
        LaunchedEffect(key1 = uiState) {
            when(uiState) {
                EditViewModel.UiState.Idle -> {
                }
                EditViewModel.UiState.Initial -> {
                    viewModel.load()
                }
                is EditViewModel.UiState.LoadError -> {}
                is EditViewModel.UiState.LoadSuccess -> {
                    title = (uiState as EditViewModel.UiState.LoadSuccess).todo.title
                    description = (uiState as EditViewModel.UiState.LoadSuccess).todo.description
                    viewModel.moveToIdle()
                }
                EditViewModel.UiState.Loading -> {}
            }
        }
    }
}

@Composable
fun EditToDoFrom(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    setTitle: (String) -> Unit,
    setDescription: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            label = { Text("Title") },
            value = title,
            onValueChange = setTitle,
            maxLines = 1,
            modifier = modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .fillMaxWidth()
        )
        OutlinedTextField(
            label = { Text("Description") },
            value = description,
            onValueChange = setDescription,
            modifier = modifier
                .padding(
                    horizontal = 16.dp
                )
                .padding(bottom = 8.dp)
                .fillMaxSize()
        )
    }
}
