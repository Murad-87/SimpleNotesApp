package com.muslim.simplenotesapp.utils

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun MyDropdownMenu(
    showMenu: MutableState<Boolean>,
    onClick: () -> Unit,
    textItem: String,
) {

    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false }
    ) {
        DropdownMenuItem(onClick = {
            onClick()
        }) {
            Text(text = textItem)
        }
    }
}