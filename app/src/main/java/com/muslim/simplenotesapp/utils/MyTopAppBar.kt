package com.muslim.simplenotesapp.utils

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MyTopAppBar(
    title: String,
    elevation: Dp = 0.dp,
    navController: NavController? = null,
    endIcon: Painter? = null,
    shouldShowStartIcon: Boolean = true,
    endIconAction: (() -> Unit)? = null,
    backgroundColor: Color = Color(0xFF2196F3),
) {

    TopAppBar(
        title = {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = title,
                    modifier = Modifier.align(Alignment.CenterStart),
                    textAlign = TextAlign.Center
                )
            }
        },
        backgroundColor = backgroundColor,
        contentColor = Color.White,
        elevation = elevation
    )
}