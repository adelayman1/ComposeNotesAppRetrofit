package com.example.noteappcompose.presentation.registerScreen.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.noteappcompose.presentation.theme.Orange
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(5.sdp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.sdp))
        } else
            Text(text = "Continue", fontSize = 13.ssp)
    }
}