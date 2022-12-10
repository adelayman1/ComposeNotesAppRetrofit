package com.example.noteappcompose.presentation.loginScreen


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteappcompose.presentation.loginScreen.components.OutlineInputField
import com.example.noteappcompose.presentation.loginScreen.uiStates.LoginUiEvent
import com.example.noteappcompose.presentation.registerScreen.components.LoadingButton
import com.example.noteappcompose.presentation.theme.HintGray
import com.example.noteappcompose.presentation.theme.LightGray
import com.example.noteappcompose.presentation.theme.Orange
import com.example.noteappcompose.presentation.theme.UbuntuFont
import com.example.noteappcompose.presentation.utility.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                LoginViewModel.UiEvent.LoginSuccess -> navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(
                        0
                    )
                }

                is LoginViewModel.UiEvent.ShowMessage -> scaffoldState.snackbarHostState.showSnackbar(
                    event.error
                )
            }
        }
    }
    Scaffold(
        modifier = Modifier.background(LightGray),
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(LightGray)
                .padding(vertical = 25.sdp, horizontal = 15.sdp)
        ) {
            Text(
                text = "Welcome back!",
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Bold,
                fontSize = 18.ssp,
                color = Color.White,
            )

            Text(
                text = "Sign in to your account",
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal,
                fontSize = 12.ssp,
                color = HintGray,
                modifier = Modifier.padding(vertical = 10.sdp),
            )
            Spacer(Modifier.size(30.sdp))
            OutlineInputField(
                text = viewModel.loginUiState.emailUiState.text,
                hint = "Email",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { viewModel.onEvent(LoginUiEvent.EmailChanged(it)) },
                keyboardType = KeyboardType.Email,
                isErrorVisible = viewModel.loginUiState.emailUiState.errorMessage != null,
                error = viewModel.loginUiState.emailUiState.errorMessage
            )
            Spacer(Modifier.size(20.sdp))
            OutlineInputField(
                text = viewModel.loginUiState.passwordUiState.text,
                hint = "Password",
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Default.Password,
                onValueChange = { viewModel.onEvent(LoginUiEvent.PasswordChanged(it)) },
                keyboardType = KeyboardType.Password,
                isErrorVisible = viewModel.loginUiState.passwordUiState.errorMessage != null,
                error = viewModel.loginUiState.passwordUiState.errorMessage
            )
            LoadingButton(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .padding(vertical = 20.sdp)
                    .fillMaxWidth()
                    .height(40.sdp),
                isEnabled = !viewModel.loginUiState.isLoading,
                isLoading = viewModel.loginUiState.isLoading
            )

//            TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "don't have an account? sign up",
                modifier = Modifier
                    .padding(top = 5.sdp)
                    .clickable {
                        navController.navigate(Screen.RegisterScreen.route)
                    },
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Normal,
                fontSize = 13.ssp,
                color = Color.White,
            )
        }
    }
}