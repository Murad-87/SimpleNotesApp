package com.muslim.simplenotesapp.presentation.screens_note

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.R
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.presentation.navigation.NavRoute
import com.muslim.simplenotesapp.ui.theme.LightGray
import com.muslim.simplenotesapp.ui.theme.MyColor
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.DB_TYPE
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM

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
    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var subtitle by remember { mutableStateOf(Constants.Keys.EMPTY) }
    val showMenu = remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(false) }
    val selectedIcon = remember { mutableStateOf(true) }
    title = note.title
    subtitle = note.subtitle


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = if (selectedIcon.value) {
                                "Note"
                            } else {
                                "Изменить"
                            },
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterStart),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                if (selectedIcon.value) {
                                    showMenu.value = true
                                } else {
                                    viewModel.updateNote(
                                        note = Note(
                                            id = note.id,
                                            title = title,
                                            subtitle = subtitle,
                                            firebaseId = note.firebaseId
                                        )
                                    ) {
                                        selectedIcon.value = true
                                    }
                                }
                            },
                        ) {
                            if (selectedIcon.value) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                },
                actions = {
                    DropdownMenu(
                        expanded = showMenu.value,
                        onDismissRequest = { showMenu.value = false }) {
                        DropdownMenuItem(onClick = {
                            viewModel.deleteNote(note = note) {
                                navController.navigate(NavRoute.Main.route) {
                                    popUpTo(NavRoute.Main.route) {
                                        inclusive = true
                                    }
                                }
                            }
                            showMenu.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                tint = LightGray
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Удалить")
                        }
                    }
                },
                backgroundColor = Color(0xFF2196F3),
                elevation = 0.dp
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    isButtonEnabled = title.isNotEmpty() && subtitle.isNotEmpty()
                    selectedIcon.value = false
                },
                placeholder = { Text(text = Constants.Keys.NOTE_TITLE, fontSize = 24.sp) },
                textStyle = TextStyle(fontSize = 24.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .border(border = BorderStroke(0.dp, color = Color.Transparent)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = MyColor,
                )
            )

            OutlinedTextField(
                value = subtitle,
                onValueChange = {
                    subtitle = it
                    isButtonEnabled = title.isNotEmpty() && subtitle.isNotEmpty()
                    selectedIcon.value = false
                },
                placeholder = { Text(text = Constants.Keys.NOTE_SUBTITLE, fontSize = 20.sp) },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .border(border = BorderStroke(0.dp, color = Color.Transparent)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = MyColor,
                )
            )
        }
    }
}
