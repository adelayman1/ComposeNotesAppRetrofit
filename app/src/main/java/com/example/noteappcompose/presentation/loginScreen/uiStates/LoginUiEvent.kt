package com.example.noteappcompose.presentation.loginScreen.uiStates

sealed class LoginUiEvent {
    data class EmailChanged(var text:String):LoginUiEvent()
    data class PasswordChanged(var text:String):LoginUiEvent()
}