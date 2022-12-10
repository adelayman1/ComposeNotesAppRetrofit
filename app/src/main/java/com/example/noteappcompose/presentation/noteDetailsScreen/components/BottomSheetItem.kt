package com.example.noteappcompose.presentation.noteDetailsScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.noteappcompose.presentation.theme.BottomBarIcon
import com.example.noteappcompose.presentation.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BottomSheetItem(icon: ImageVector, text: String, onClick: () -> Unit,color:Color = BottomBarIcon) {
    Row(
        Modifier
            .padding(start = 10.sdp)
            .fillMaxWidth()
            .height(35.sdp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(22.sdp),
            tint = color
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 10.sdp),
            fontFamily = UbuntuFont,
            fontWeight = FontWeight.Medium,
            color = color,
            fontSize = 12.ssp,
        )
    }
}