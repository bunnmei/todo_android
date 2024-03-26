package com.example.build_test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.build_test.db.Todo

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    viewModel: ToDoListViewModel,
    toCreateScreen: () -> Unit,
    update: (Todo) -> Unit,
    todoEdit: (Long) -> Unit,
) {
    val list = viewModel.items.collectAsState(initial = emptyList())
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = toCreateScreen ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
                contentPadding = it
                ){

            items(
                count = list.value.size,
                key = {i -> list.value[i].id },
                itemContent = {
                    TodoListItem(
                        todo = list.value[it],
                        update = update,
                        onClick = {
                            todoEdit(list.value[it].id)
                        },
                    )
                }
            )
        }
    }
}

@Composable
fun TodoListItem(
    todo: Todo,
    update: (Todo) ->Unit,
    onClick:() -> Unit
) {

    ListItem(
        headlineContent = { Text(todo.title) },
        leadingContent = {
            Checkbox(checked = todo.isDone, onCheckedChange = {
                update(todo.copy(isDone = it))
            })
        },
        modifier = Modifier.clickable { onClick() }
    )
    Divider()
}
