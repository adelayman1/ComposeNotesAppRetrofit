package com.example.noteappcompose.presentation.homeScreen.uiStates

import androidx.compose.ui.graphics.toArgb
import com.example.noteappcompose.presentation.utility.Constants

data class NoteItemUiState(
    var id:String = "-1",
    var noteTitle: String = "",
    var noteSubtitle: String = "",
    var noteDate: String = "",
    var noteColor: Int = Constants.noteColorsList[0].toArgb(),
    var imageLink: String? = null,
    var isImageVisible: Boolean = false
)