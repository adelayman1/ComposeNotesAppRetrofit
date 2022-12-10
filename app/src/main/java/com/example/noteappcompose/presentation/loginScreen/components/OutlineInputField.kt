package com.example.noteappcompose.presentation.loginScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.noteappcompose.presentation.theme.BottomBarIcon
import com.example.noteappcompose.presentation.theme.Orange
import com.example.noteappcompose.presentation.theme.Red
import com.example.noteappcompose.presentation.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlineInputField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    icon:ImageVector = Icons.Default.Email,
    error: String? = null,
    isErrorVisible: Boolean = false,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    Column {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            label = {
                Text(hint, color = BottomBarIcon)
            },
            leadingIcon = {
                Icon(
                    icon,
                    contentDescription = hint,
                    tint = BottomBarIcon
                )
            },
            shape = RoundedCornerShape(5.sdp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                focusedBorderColor = BottomBarIcon,
                selectionColors = TextSelectionColors(
                    handleColor = Orange,
                    backgroundColor = Orange.copy(alpha = 0.4f)
                ),
                unfocusedBorderColor = BottomBarIcon,
                cursorColor = Orange
            ),
        )
        AnimatedVisibility(isErrorVisible) {
            Text(
                text = error ?: "",
                modifier = Modifier
                    .padding(start = 13.sdp, top = 3.sdp)
                    .fillMaxWidth(),
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal,
                fontSize = 10.ssp,
                color = Red
            )
        }
    }
}
