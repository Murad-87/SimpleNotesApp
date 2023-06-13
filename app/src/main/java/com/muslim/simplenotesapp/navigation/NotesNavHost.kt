package com.muslim.simplenotesapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.calculator.screen.CalculatorMainScreen
import com.muslim.simplenotesapp.screens.AddScreen
import com.muslim.simplenotesapp.screens.MainScreen
import com.muslim.simplenotesapp.screens.NoteScreen
import com.muslim.simplenotesapp.screens.StartScreen
import com.muslim.simplenotesapp.utils.Constants

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesNavHost(viewModel: MainViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavRoute.Calculator.route) {
        composable(NavRoute.Calculator.route) {
            CalculatorMainScreen(navController = navController)
        }
        composable(NavRoute.Start.route) {
            StartScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Main.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Add.route) {
            AddScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavRoute.Note.route + "/{${Constants.Keys.ID}}") { backStackEntry ->
            NoteScreen(
                navController = navController,
                viewModel = viewModel,
                noteId = backStackEntry.arguments?.getString(Constants.Keys.ID)
            )
        }
    }
}