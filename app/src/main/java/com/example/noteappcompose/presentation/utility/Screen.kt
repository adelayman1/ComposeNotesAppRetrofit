package com.example.noteappcompose.presentation.utility

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object HomeScreen: Screen("home_screen")
    object NoteDetailsScreen: Screen("note_details_screen")
    object SplashScreen: Screen("splash_screen")
}
