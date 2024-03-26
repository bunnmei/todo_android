package com.example.build_test

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home"){
        composable("home"){
        val viewModel: ToDoListViewModel = hiltViewModel()
            TodoListScreen(
                viewModel = viewModel,
                update = {todo ->
                    viewModel.update(todo)
                },
                toCreateScreen = {
                    navController.navigate("detail")
                },
                todoEdit = {id ->
                    navController.navigate("/todos/$id")
                }
            )
        }
        composable("detail"){
            val viewModel: CreateViewModel = hiltViewModel()
            CreateToDoScreen(
                viewModel  = viewModel,
            ){
                navController.popBackStack()
            }
        }

        composable("/todos/{todoId}", arguments = listOf(
            navArgument("todoId"){
                type = NavType.LongType
            }
        )
        ){
            val viewModel: EditViewModel = hiltViewModel()
            EditToDoScreen(viewModel = viewModel)
        }
    }
}

