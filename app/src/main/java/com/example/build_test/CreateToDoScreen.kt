package com.example.build_test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CreateToDoScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateViewModel,
    back: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    CreateToDoScreen(
        uiState = uiState,
        back = back,
        create = { title, description ->
            viewModel.create(title, description)
        },
        moveToIdle = {
            viewModel.moveToIdle()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateToDoScreen(
    modifier: Modifier = Modifier,
    uiState: CreateViewModel.UiState,
    back: () -> Unit,
    create: (String, String) -> Unit,
    moveToIdle: () -> Unit,
) {
    val ctx = LocalContext.current
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var description by rememberSaveable {
        mutableStateOf("")
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState) {
        when(uiState) {
            is CreateViewModel.UiState.CreateError -> {
                snackbarHostState.showSnackbar(
                    message = uiState.e.toString()
                )
                moveToIdle()
            }
            CreateViewModel.UiState.Idle -> {}
            CreateViewModel.UiState.InputError -> {
                snackbarHostState.showSnackbar(
                    message = ctx.getString(R.string.title_empty)
                )
                moveToIdle()
            }
            CreateViewModel.UiState.Success -> {
                back()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.create_todo_title)) },
                navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        create(title, description)
                    }) {
                        Icon(Icons.Filled.Done, contentDescription = "Create")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            OutlinedTextField(
                label = { Text("Title") },
                value = title,
                onValueChange = { title = it},
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
                onValueChange = { description = it },
                modifier = modifier
                    .padding(
                        horizontal = 16.dp
                    )
                    .padding(bottom = 8.dp)
                    .fillMaxSize()
            )
        }
    }
}