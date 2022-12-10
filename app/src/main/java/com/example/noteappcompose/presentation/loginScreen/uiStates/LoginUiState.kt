package com.example.noteappcompose.presentation.loginScreen.uiStates

import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.InputFieldUiState

data class LoginUiState(
    val isLoading: Boolean = false,
    val emailUiState: InputFieldUiState = InputFieldUiState(),
    val passwordUiState: InputFieldUiState = InputFieldUiState()
)