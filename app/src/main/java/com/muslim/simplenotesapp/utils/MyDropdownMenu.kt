package com.muslim.simplenotesapp.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MyDropdownMenu(
    showMenu: MutableState<Boolean>,
    onClick: () -> Unit,
    textItem: String,
    icon: Painter? = null,
    iconColorTint: Color = Color.Black,
) {

    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false }
    ) {
        DropdownMenuItem(onClick = {
            onClick()
        }) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = iconColorTint
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = textItem)
        }
    }
}