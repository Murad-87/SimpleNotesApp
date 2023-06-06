package com.muslim.simplenotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muslim.simplenotesapp.screens.AddScreen
import com.muslim.simplenotesapp.screens.MainScreen
import com.muslim.simplenotesapp.screens.NoteScreen
import com.muslim.simplenotesapp.screens.StartScreen

@Composable
fun NotesNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) {
            StartScreen(navController = navController)
        }
        composable(NavRoute.Main.route) {
            MainScreen(navController = navController)
        }
        composable(NavRoute.Add.route) {
            AddScreen(navController = navController)
        }
        composable(NavRoute.Note.route) {
            NoteScreen(navController = navController)
        }
    }
}