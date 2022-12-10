package com.example.noteappcompose.presentation.homeScreen.uiStates

data class NotesUiState(
    var isLoading: Boolean = true,
    var notes: List<NoteItemUiState> = emptyList(),
    var searchText: String="",
    var searchResult: List<NoteItemUiState> = emptyList()
)