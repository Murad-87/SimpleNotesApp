package com.muslim.simplenotesapp.presentation.screens_note

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.MainViewModelFactory
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.presentation.navigation.NavRoute
import com.muslim.simplenotesapp.ui.theme.SimpleNotesAppTheme
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.MyTopAppBar

@Composable
fun AddScreen(navController: NavHostController, viewModel: MainViewModel) {

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(title = "Add new note")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    isButtonEnabled = title.isNotEmpty() && subtitle.isNotEmpty()
                },
                placeholder = { Text(text = Constants.Keys.NOTE_TITLE) },
                isError = title.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedTextField(
                value = subtitle,
                onValueChange = {
                    subtitle = it
                    isButtonEnabled = title.isNotEmpty() && subtitle.isNotEmpty()
                },
                placeholder = { Text(text = Constants.Keys.NOTE_SUBTITLE) },
                isError = title.isEmpty(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(160.dp),
                enabled = isButtonEnabled,
                onClick = {
                    viewModel.addNote(note = Note(title = title, subtitle = subtitle)) {
                        navController.navigate(NavRoute.Main.route) {
                            popUpTo(NavRoute.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                }) {
                Text(
                    text = Constants.Keys.ADD_NOTE,
                    fontSize = 19.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    SimpleNotesAppTheme {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        AddScreen(navController = rememberNavController(), viewModel = viewModel)
    }
}