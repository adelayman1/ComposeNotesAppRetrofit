package com.example.noteappcompose.presentation.homeScreen.uiStates

sealed class HomeUiEvent {
    data class SearchTextChanged(var text:String):HomeUiEvent()
    object Search:HomeUiEvent()
}