package com.muslim.simplenotesapp.presentation.screens_note

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.muslim.simplenotesapp.utils.DB_TYPE
import com.muslim.simplenotesapp.utils.MyTopAppBar
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = when (DB_TYPE.value) {
                    TYPE_ROOM -> "Local storage"
                    TYPE_FIREBASE -> "Remote storage"
                    else -> "Notes App"
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRoute.Add.route)
                },
                modifier = Modifier.padding(bottom = 26.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Icons",
                    tint = Color.White
                )
            }
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .background(Color(0xFFE2EEED))
        ) {
            items(notes) { note ->
                NoteItem(note = note, navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteItem(note: Note, navController: NavHostController) {

    val noteId = when (DB_TYPE.value) {
        TYPE_FIREBASE -> note.firebaseId
        TYPE_ROOM -> note.id
        else -> Constants.Keys.EMPTY
    }
    val currentCreateDate = remember {
        mutableStateOf(LocalDate.now())
    }
//    val simpleDateFormat = SimpleDateFormat("dd\MM\yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .clickable {
                navController.navigate(NavRoute.Note.route + "/${noteId}")
            },
        elevation = 6.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = note.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = note.subtitle,
                )
            }
            Text(
                text = currentCreateDate.value.format(DateTimeFormatter.ISO_DATE),
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    SimpleNotesAppTheme {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        MainScreen(navController = rememberNavController(), viewModel = viewModel)
    }
}