package com.muslim.simplenotesapp.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muslim.simplenotesapp.MainViewModel
import com.muslim.simplenotesapp.MainViewModelFactory
import com.muslim.simplenotesapp.navigation.NavRoute
import com.muslim.simplenotesapp.ui.theme.SimpleNotesAppTheme
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.Constants.Keys.FIREBASE_DATABASE
import com.muslim.simplenotesapp.utils.Constants.Keys.LOGIN_TEXT
import com.muslim.simplenotesapp.utils.Constants.Keys.PASSWORD_TEXT
import com.muslim.simplenotesapp.utils.Constants.Keys.ROOM_DATABASE
import com.muslim.simplenotesapp.utils.Constants.Keys.SING_IN
import com.muslim.simplenotesapp.utils.Constants.Keys.WHAT_WILL_WE_USE
import com.muslim.simplenotesapp.utils.DB_TYPE
import com.muslim.simplenotesapp.utils.LOGIN
import com.muslim.simplenotesapp.utils.MyTopAppBar
import com.muslim.simplenotesapp.utils.PASSWORD
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var login by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY) }
    val focusManager = LocalFocusManager.current

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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Constants.Keys.LOG_IN,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp, bottom = 18.dp)
                    )
                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it },
                        label = { Text(text = LOGIN_TEXT) },
                        isError = login.isEmpty(),
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Email
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(text = PASSWORD_TEXT) },
                        isError = password.isEmpty(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .width(200.dp),
                        onClick = {
                            LOGIN = login
                            PASSWORD = password
                            viewModel.initDatabase(TYPE_FIREBASE) {
                                DB_TYPE.value = TYPE_FIREBASE
                                navController.navigate(NavRoute.Main.route) {
                                    popUpTo(NavRoute.Main.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        enabled = login.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(
                            text = SING_IN,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MyTopAppBar(title = "Notes App")
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 38.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = WHAT_WILL_WE_USE,
                    fontSize = 18.sp
                )
                Button(
                    onClick = {
                        viewModel.initDatabase(TYPE_ROOM) {
                            DB_TYPE.value = TYPE_ROOM
                            navController.navigate(route = NavRoute.Main.route)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = ROOM_DATABASE,
                        fontSize = 20.sp
                    )
                }

                Button(
                    onClick = {
                        scope.launch {
                            bottomSheetState.show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = FIREBASE_DATABASE,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    SimpleNotesAppTheme {
        val context = LocalContext.current
        val viewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        StartScreen(navController = rememberNavController(), viewModel = viewModel)
    }
}