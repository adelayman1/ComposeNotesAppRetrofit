package com.example.noteappcompose.presentation.splashScreen.uiStates

import com.example.noteappcompose.presentation.utilities.Screen

sealed class SplashUiEvent(var screen:Screen){
    object HomeScreen:SplashUiEvent(Screen.HomeScreen)
    object LoginScreen:SplashUiEvent(Screen.LoginScreen)
}