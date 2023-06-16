package com.muslim.simplenotesapp.presentation.screens_note

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.muslim.simplenotesapp.ui.theme.MyColor
import com.muslim.simplenotesapp.ui.theme.SimpleNotesAppTheme
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.DB_TYPE
import com.muslim.simplenotesapp.utils.MyTopAppBar
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {

    val notes = viewModel.readAllNotes().observeAsState().value
    val note = when (DB_TYPE.value) {
        TYPE_ROOM -> {
            notes?.firstOrNull { it.id == noteId?.toInt() } ?: Note()
        }

        TYPE_FIREBASE -> {
            notes?.firstOrNull { it.firebaseId == noteId } ?: Note()
        }

        else -> Note()
    }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var subtitle by remember { mutableStateOf(Constants.Keys.EMPTY) }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 32.dp, end = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = Constants.Keys.EDIT_NOTE,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text(text = Constants.Keys.TITLE) },
                        isError = title.isEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = { subtitle = it },
                        label = { Text(text = Constants.Keys.SUBTITLE) },
                        isError = subtitle.isEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            viewModel.updateNote(
                                note = Note(
                                    id = note.id,
                                    title = title,
                                    subtitle = subtitle,
                                    firebaseId = note.firebaseId
                                )
                            ) {
                                navController.navigate(NavRoute.Main.route) {
                                    popUpTo(NavRoute.Main.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        enabled = subtitle.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.UPDATE_NOTE)
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(title = "Note")
            },
            modifier = Modifier
                .fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE2EEED))
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 6.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = note.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 6.dp),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = note.subtitle,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(modifier = Modifier
                        .weight(0.5f),
                        colors = ButtonDefaults.buttonColors(MyColor),
                        onClick = {
                            scope.launch {
                                title = note.title
                                subtitle = note.subtitle
                                bottomSheetState.show()
                            }
                        }) {
                        Text(
                            text = Constants.Keys.UPDATE,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Button(modifier = Modifier
                        .weight(0.5f),
                        colors = ButtonDefaults.buttonColors(MyColor),
                        onClick = {
                            viewModel.deleteNote(note = note) {
                                navController.navigate(NavRoute.Main.route) {
                                    popUpTo(NavRoute.Main.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }) {
                        Text(
                            text = Constants.Keys.DELETE,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    SimpleNotesAppTheme {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        NoteScreen(
            navController = rememberNavController(),
            viewModel = viewModel,
            noteId = "1"
        )
    }
}