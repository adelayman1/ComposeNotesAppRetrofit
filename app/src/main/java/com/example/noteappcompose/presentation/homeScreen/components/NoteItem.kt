package com.example.noteappcompose.presentation.homeScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import coil.compose.SubcomposeAsyncImage
import com.example.noteappcompose.presentation.homeScreen.uiStates.NoteItemUiState
import com.example.noteappcompose.presentation.theme.NoteSubtitle
import com.example.noteappcompose.presentation.theme.UbuntuFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(note: NoteItemUiState, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(10.sdp),
        colors = CardDefaults.cardColors(containerColor = Color(note.noteColor)),
    ) {
        if(note.isImageVisible){
            SubcomposeAsyncImage(
                model = note.imageLink,
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(RoundedCornerShape(topEnd = 10.sdp, topStart = 10.sdp))
            )
        }
        Text(
            text = note.noteTitle,
            fontFamily = UbuntuFont,
            fontWeight = FontWeight.Bold,
            fontSize = 13.ssp,
            color = Color.White,
            modifier = Modifier.padding(top = 8.sdp, end = 8.sdp, start = 8.sdp)
        )
        Text(
            text = note.noteSubtitle,
            fontFamily = UbuntuFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            color = NoteSubtitle,
            modifier = Modifier.padding(
                top = 4.sdp, end = 8.sdp, start = 8.sdp, bottom = 4.sdp
            )
        )
        Text(
            text = note.noteDate,
            fontFamily = UbuntuFont,
            fontWeight = FontWeight.Normal,
            fontSize = 7.ssp,
            color = NoteSubtitle,
            modifier = Modifier.padding(all = 8.sdp)
        )
    }
}