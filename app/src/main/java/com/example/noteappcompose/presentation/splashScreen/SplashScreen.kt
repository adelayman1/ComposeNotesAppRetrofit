package com.example.noteappcompose.presentation.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteappcompose.R
import com.example.noteappcompose.presentation.splashScreen.uiStates.SplashUiEvent
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                SplashUiEvent.HomeScreen -> navController.navigate(event.screen.route){
                    popUpTo(0)
                }
                SplashUiEvent.LoginScreen -> navController.navigate(event.screen.route){
                    popUpTo(0)
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.jetpack_compose_icon),
            contentDescription = null
        )
    }
}