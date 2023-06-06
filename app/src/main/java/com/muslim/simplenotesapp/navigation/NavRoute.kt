package com.muslim.simplenotesapp.navigation

sealed class NavRoute(val route: String) {

    object Start: NavRoute("start_screen")
    object Main: NavRoute("main_screen")
    object Add: NavRoute("add_screen")
    object Note: NavRoute("note_screen")
}
