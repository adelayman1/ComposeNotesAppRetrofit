package com.example.noteappcompose.presentation.noteDetailsScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.example.noteappcompose.presentation.theme.Orange
import com.example.noteappcompose.presentation.theme.SearchGray
import com.example.noteappcompose.presentation.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun UrlDialog(
    text: String,
    error: String? = null,
    isErrorVisible: Boolean = false,
    onTextChange: (String) -> Unit,
    onClickAdd: () -> Unit,
    onClickDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onClickDismiss() }) {
        Surface(
            shape = RoundedCornerShape(10.sdp),
            color = SearchGray
        ) {
            Box() {
                Column(Modifier.padding(8.sdp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Language,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.sdp),
                            tint = Color.White
                        )
                        Text(
                            text = "Add URL",
                            modifier = Modifier.padding(start = 8.sdp),
                            fontFamily = UbuntuFont,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            fontSize = 14.ssp,
                        )
                    }
                    NoteInputField(
                        text = text,
                        hint = "Enter Url",
                        error = error,
                        isErrorVisible = isErrorVisible,
                        onValueChange = onTextChange,
                        textStyle = TextStyle(fontSize = 13.ssp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.sdp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onClickDismiss) {
                            Text(text = "CANCEL", color = Orange)
                        }
                        TextButton(onClick = onClickAdd) {
                            Text(text = "ADD", color = Orange)
                        }
                    }
                }
            }
        }
    }
}