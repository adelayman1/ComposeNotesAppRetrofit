package com.example.noteappcompose.presentation.noteDetailsScreen.uiStates

import android.net.Uri

sealed class NoteDetailsEvent {
    data class ClickColor(var color: Int) : NoteDetailsEvent()
    data class TitleChanged(var text: String) : NoteDetailsEvent()
    data class SubtitleChanged(var text: String) : NoteDetailsEvent()
    data class NoteTextChanged(var text: String) : NoteDetailsEvent()
    object SaveNote : NoteDetailsEvent()
    object DismissUrlDialog : NoteDetailsEvent()
    object AddUrlDialog : NoteDetailsEvent()
    object ShowUrlDialog : NoteDetailsEvent()
    object DeleteUrl : NoteDetailsEvent()

    data class UrlTextChanged(var text: String) : NoteDetailsEvent()
    data class SelectedImage(var image: Uri) : NoteDetailsEvent()
    object DeleteImage : NoteDetailsEvent()
}