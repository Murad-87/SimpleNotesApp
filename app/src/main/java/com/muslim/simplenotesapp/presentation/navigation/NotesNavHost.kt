package com.muslim.simplenotesapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.presentation.screens_calculator.CalculatorMainScreen
import com.muslim.simplenotesapp.presentation.screens_note.AddScreen
import com.muslim.simplenotesapp.presentation.screens_note.MainScreen
import com.muslim.simplenotesapp.presentation.screens_note.NoteScreen
import com.muslim.simplenotesapp.presentation.screens_note.StartScreen
import com.muslim.simplenotesapp.utils.Constants

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavRoute.Calculator.route) {
        composable(NavRoute.Calculator.route) {
            CalculatorMainScreen(navController = navController)
        }
        composable(NavRoute.Start.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            StartScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Main.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Add.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            AddScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Note.route + "/{${Constants.Keys.ID}}") { backStackEntry ->
            val viewModel = hiltViewModel<MainViewModel>()
            NoteScreen(
                navController = navController,
                viewModel = viewModel,
                noteId = backStackEntry.arguments?.getString(Constants.Keys.ID)
            )
        }
    }
}