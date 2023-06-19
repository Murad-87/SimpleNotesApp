package com.muslim.simplenotesapp.utils

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
    shouldShowStartIcon: Boolean = false,
    endIconAction: (() -> Unit)? = null,
    backgroundColor: Color = Color(0xFF2196F3),
    endIconColorTint: Color = Color.Black,
    actions: @Composable RowScope.() -> Unit = {},
    endIconEnabled: Boolean = false
) {

    TopAppBar(
        title = {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                if (shouldShowStartIcon) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
                if (endIcon != null) {
                    IconButton(
                        onClick = { endIconAction?.invoke() },
                        modifier = Modifier.align(Alignment.CenterEnd),
                        enabled = endIconEnabled
                    ) {
                        Icon(
                            painter = endIcon,
                            contentDescription = null,
                            tint = endIconColorTint,

                        )
                    }
                }
                Text(
                    text = title,
                    modifier = Modifier.align(Alignment.CenterStart),
                    textAlign = TextAlign.Center
                )
            }
        },
        backgroundColor = backgroundColor,
        contentColor = Color.White,
        elevation = elevation,
        actions = actions,

    )
}