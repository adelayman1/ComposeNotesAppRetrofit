package com.example.noteappcompose.presentation.noteDetailsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.noteappcompose.presentation.theme.SearchGray
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ColorBox(
    color: Color= SearchGray,
    isCheckIconVisible: Boolean = false,
    onClick:() -> Unit,
) {
    Box(
        contentAlignment= Alignment.Center,
        modifier = Modifier
            .size(35.sdp)
            .border(
                width = 2.sdp,
                color = color,
                shape = CircleShape
            ).clickable {
                onClick()
            },
    ){
        if(isCheckIconVisible){

            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "contentDescription",
                modifier = Modifier
                    .size(23.sdp)
                    .background(color, CircleShape)
                    .padding(4.sdp),
                tint = Color.White
            )
        }else{
            Box(
                modifier = Modifier
                    .size(23.sdp)
                    .background(color, CircleShape)
                    .padding(4.sdp)
            )
        }
    }
}