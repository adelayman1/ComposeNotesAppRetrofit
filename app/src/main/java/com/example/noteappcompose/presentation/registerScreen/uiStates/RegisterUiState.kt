package com.example.noteappcompose.presentation.registerScreen.uiStates

import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.InputFieldUiState

data class RegisterUiState(
    val isLoading: Boolean = false,
    val nameUiState: InputFieldUiState = InputFieldUiState(),
    val emailUiState: InputFieldUiState = InputFieldUiState(),
    val passwordUiState: InputFieldUiState = InputFieldUiState()
)