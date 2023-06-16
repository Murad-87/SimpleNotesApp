package com.muslim.simplenotesapp.utils

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun MyAlertDialog(
    title: String,
    secretNumber: MutableState<String>,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    dialogState: MutableState<Boolean>,

    ) {

    val focusManager = LocalFocusManager.current

    AlertDialog(onDismissRequest = {
        dialogState.value = false
    },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(
                    text = "Запомнить",
                    style = TextStyle(color = Color.Black),
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onCancel()
            }) {
                Text(
                    text = "Отменить",
                    style = TextStyle(color = Color.Black),
                    fontSize = 16.sp
                )
            }
        },
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            OutlinedTextField(
                value = secretNumber.value,
                onValueChange = { secretNumber.value = it },
                placeholder = {
                    Text(text = "Ваше число")
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
            )
        }
    )
}