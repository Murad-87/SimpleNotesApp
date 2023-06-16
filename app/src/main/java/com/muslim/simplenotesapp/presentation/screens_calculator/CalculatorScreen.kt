package com.muslim.simplenotesapp.presentation.screens_calculator

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muslim.simplenotesapp.R
import com.muslim.simplenotesapp.data.data_calculator.CalculatorAction
import com.muslim.simplenotesapp.data.data_calculator.CalculatorOperation
import com.muslim.simplenotesapp.data.data_calculator.CalculatorState
import com.muslim.simplenotesapp.data.data_store.DataStoreManager
import com.muslim.simplenotesapp.data.data_store.SecretNumberData
import com.muslim.simplenotesapp.presentation.navigation.NavRoute
import com.muslim.simplenotesapp.ui.theme.LightGray
import com.muslim.simplenotesapp.ui.theme.MediumGray
import com.muslim.simplenotesapp.ui.theme.Orange
import com.muslim.simplenotesapp.utils.MyAlertDialog
import com.muslim.simplenotesapp.utils.MyDropdownMenu
import com.muslim.simplenotesapp.utils.MyTopAppBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CalculatorScreen(
    state: CalculatorState,
    modifier: Modifier,
    buttonSpacing: Dp = 6.dp,
    onAction: (CalculatorAction) -> Unit,
    navController: NavHostController
) {

    val viewModel = viewModel<CalculatorViewModel>()
    val showMenu = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val scope = rememberCoroutineScope()
    val dialogState = remember {
        mutableStateOf(false)
    }
    val secretNumber = remember {
        mutableStateOf("")
    }
    val savedNumberState = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        dataStoreManager.getNumber().collect { number ->
            savedNumberState.value = number
            Log.d("TEST", "Secret get number: $savedNumberState")
        }
    }
    val result = viewModel.state.number1

    if (dialogState.value) {
        MyAlertDialog(
            title = "Придумайте и запомните число.",
            secretNumber = secretNumber,
            onConfirm = {
                scope.launch(Dispatchers.IO) {
                    dataStoreManager.saveNumber(
                        SecretNumberData(
                            number = secretNumber.value.toInt()
                        )
                    )
                    Log.d("TEST", "Secret number: $secretNumber")
                }
                dialogState.value = false
            },
            onCancel = {
                dialogState.value = false
            },
            dialogState = dialogState
        )
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Calculator",
                backgroundColor = LightGray,
                endIcon = painterResource(id = R.drawable.ic_more_vert),
                endIconColorTint = MediumGray,
                endIconAction = {
                    if (savedNumberState.value == 0) {
                        showMenu.value = !showMenu.value
                    } else {
                        showMenu.value = false
                        Toast.makeText(context, "Число задано!", Toast.LENGTH_LONG).show()
                    }
                },
                actions = {
                    MyDropdownMenu(showMenu = showMenu,
                        onClick = {
                            dialogState.value = true
                            showMenu.value = false
                        }) {
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = modifier) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                Text(
                    text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 66.sp,
                    color = Color.White,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "AC",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(2f)
                            .weight(2f),
                        onClick = {
                            onAction(CalculatorAction.Clear)
                        }
                    )
                    CalculatorButton(
                        symbol = "Del",
                        modifier = Modifier
                            .background(LightGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Delete)
                        }
                    )
                    CalculatorButton(
                        symbol = "/",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "7",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(7))
                        }
                    )
                    CalculatorButton(
                        symbol = "8",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(8))
                        }
                    )
                    CalculatorButton(
                        symbol = "9",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(9))
                        }
                    )
                    CalculatorButton(
                        symbol = "x",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "4",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(4))
                        }
                    )
                    CalculatorButton(
                        symbol = "5",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(5))
                        }
                    )
                    CalculatorButton(
                        symbol = "6",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(6))
                        }
                    )
                    CalculatorButton(
                        symbol = "-",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "1",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(1))
                        }
                    )
                    CalculatorButton(
                        symbol = "2",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(2))
                        }
                    )
                    CalculatorButton(
                        symbol = "3",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Number(3))
                        }
                    )
                    CalculatorButton(
                        symbol = "+",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Operation(CalculatorOperation.Add))
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    CalculatorButton(
                        symbol = "0",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(2f)
                            .weight(2f),
                        onClick = {
                            onAction(CalculatorAction.Number(0))
                        }
                    )
                    CalculatorButton(
                        symbol = ".",
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Decimal)
                        }
                    )
                    CalculatorButton(
                        symbol = "=",
                        modifier = Modifier
                            .background(Orange)
                            .aspectRatio(1f)
                            .weight(1f),
                        onClick = {
                            onAction(CalculatorAction.Calculate)
                            if (savedNumberState.value == result.toInt()) {
                                navController.navigate(NavRoute.Start.route)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculatorScreen() {
    CalculatorScreen(
        state = CalculatorState(),
        modifier = Modifier,
        onAction = {},
        navController = rememberNavController()
    )
}