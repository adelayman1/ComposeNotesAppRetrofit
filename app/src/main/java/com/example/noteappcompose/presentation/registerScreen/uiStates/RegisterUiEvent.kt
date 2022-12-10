package com.example.noteappcompose.presentation.registerScreen.uiStates

sealed class RegisterUiEvent {
    data class NameChanged(var text:String):RegisterUiEvent()
    data class EmailChanged(var text:String):RegisterUiEvent()
    data class PasswordChanged(var text:String):RegisterUiEvent()
}