package com.example.noteappcompose.presentation.noteDetailsScreen.components

import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.noteappcompose.presentation.theme.BottomBarIcon

@Composable
fun ToolBarIconButton(icon: ImageVector, modifier: Modifier =Modifier,onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            icon,
            contentDescription = null,
            modifier = modifier,
            tint = BottomBarIcon
        )
    }
}