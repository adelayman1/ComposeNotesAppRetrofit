package com.example.noteappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteappcompose.presentation.homeScreen.HomeScreen
import com.example.noteappcompose.presentation.loginScreen.LoginScreen
import com.example.noteappcompose.presentation.noteDetailsScreen.NoteDetailsScreen
import com.example.noteappcompose.presentation.registerScreen.RegisterScreen
import com.example.noteappcompose.presentation.splashScreen.SplashScreen
import com.example.noteappcompose.presentation.theme.LightGray
import com.example.noteappcompose.presentation.theme.NoteAppComposeTheme
import com.example.noteappcompose.presentation.utility.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = LightGray
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Screen.SplashScreen.route
                    ) {
                        composable(route = Screen.SplashScreen.route) {
                            SplashScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.RegisterScreen.route) {
                            RegisterScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.NoteDetailsScreen.route + "?noteId={noteId}",
                            arguments = listOf(navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.StringType
                                defaultValue = "-1"
                            })) {
                            NoteDetailsScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}