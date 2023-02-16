package com.example.noteappcompose.presentation.noteDetailsScreen.uiStates

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.noteappcompose.presentation.utilities.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NoteDetailsUiState(
    val isLoading:Boolean = false,
    var id: String = "-1",
    var titleInputFieldUiState: InputFieldUiState = InputFieldUiState(),
    var subtitleInputFieldUiState: InputFieldUiState = InputFieldUiState(),
    var descriptionInputFieldUiState: InputFieldUiState = InputFieldUiState(),
    var noteColors: List<Color> = Constants.noteColorsList,
    var noteColor: Int = noteColors[0].toArgb(),
    var dateTime: String = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(
        Date()
    ),
    var linkUiState: LinkUiState = LinkUiState(),
    var imageLink: String? = null,
    var isImageVisible: Boolean = false
)