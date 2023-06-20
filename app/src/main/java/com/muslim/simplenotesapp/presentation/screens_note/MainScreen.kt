package com.muslim.simplenotesapp.presentation.screens_note

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.R
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.presentation.navigation.NavRoute
import com.muslim.simplenotesapp.ui.theme.LightGray
import com.muslim.simplenotesapp.ui.theme.MediumGray
import com.muslim.simplenotesapp.ui.theme.MyColor
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.DB_TYPE
import com.muslim.simplenotesapp.utils.MyDropdownMenu
import com.muslim.simplenotesapp.utils.MyTopAppBar
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val showMenu = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = when (DB_TYPE.value) {
                    TYPE_ROOM -> "Локальное хранилище"
                    TYPE_FIREBASE -> "Удаленное хранилище"
                    else -> "Notes App"
                },
                endIcon = painterResource(id = R.drawable.ic_more_vert),
                endIconColorTint = Color.White,
                endIconEnabled = true,
                endIconAction = { showMenu.value = true },
                actions = {
                    MyDropdownMenu(
                        showMenu = showMenu,
                        onClick = {
                            viewModel.deleteAllNotes()
                            showMenu.value = false
                        },
                        icon = painterResource(id = R.drawable.ic_delete_all),
                        textItem = "Удалить все",
                        iconColorTint = LightGray
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRoute.Add.route)
                },
                modifier = Modifier.padding(bottom = 26.dp, end = 16.dp),
                backgroundColor = MyColor
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
    val currentDate = note.createdDate
    val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
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
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 6.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = note.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Text(
                    text = note.subtitle,
                    maxLines = 2
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dateFormatter.format(currentDate),
                    textAlign = TextAlign.Start
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                        tint = MediumGray
                    )
                }
            }

        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    SimpleNotesAppTheme {
//        val context = LocalContext.current
//        val viewModel: MainViewModel =
//            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
//        MainScreen(navController = rememberNavController(), viewModel = viewModel)
//    }
//}