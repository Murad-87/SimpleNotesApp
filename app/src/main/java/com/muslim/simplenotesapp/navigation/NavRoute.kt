package com.muslim.simplenotesapp.navigation

import com.muslim.simplenotesapp.utils.Constants

sealed class NavRoute(val route: String) {

    object Start: NavRoute(Constants.Screens.START_SCREEN)
    object Main: NavRoute(Constants.Screens.MAIN_SCREEN)
    object Add: NavRoute(Constants.Screens.ADD_SCREEN)
    object Note: NavRoute(Constants.Screens.NOTE_SCREEN)
}
