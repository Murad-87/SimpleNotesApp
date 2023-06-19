package com.muslim.simplenotesapp.presentation.screens_note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.R
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.presentation.navigation.NavRoute
import com.muslim.simplenotesapp.ui.theme.MyColor
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.MyTopAppBar

@Composable
fun AddScreen(navController: NavHostController, viewModel: MainViewModel) {

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Создать заметку",
                endIcon = painterResource(id = R.drawable.ic_add),
                endIconColorTint = if (isButtonEnabled) {
                    Color.White
                } else {
                    Color.Gray
                },
                endIconEnabled = isButtonEnabled,
                endIconAction = {
                    viewModel.addNote(note = Note(title = title, subtitle = subtitle)) {
                        navController.navigate(NavRoute.Main.route) {
                            popUpTo(NavRoute.Main.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE2EEED))
                .padding(paddingValues)
                .padding(horizontal = 6.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AddScreenPreview() {
//    SimpleNotesAppTheme {
//        val context = LocalContext.current
//        val viewModel: MainViewModel =
//            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
//        AddScreen(navController = rememberNavController(), viewModel = viewModel)
//    }
//}