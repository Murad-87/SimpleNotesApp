package com.muslim.simplenotesapp.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.muslim.simplenotesapp.ui.theme.MediumGray

@Composable
fun CalculatorMainScreen(navController: NavHostController) {
    val viewModel = viewModel<CalculatorViewModel>()
    val state = viewModel.state
    val buttonSpacing = 6.dp

    CalculatorScreen(
        state = state,
        onAction = viewModel::onAction,
        buttonSpacing = buttonSpacing,
        navController = navController,
        modifier = Modifier
            .fillMaxSize()
            .background(MediumGray)
            .padding(16.dp)
    )
}