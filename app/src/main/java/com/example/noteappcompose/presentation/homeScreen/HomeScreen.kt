package com.example.noteappcompose.presentation.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.noteappcompose.data.utilities.Constants.CREATE_NEW_NOTE_STATE_ID
import com.example.noteappcompose.presentation.homeScreen.components.NoteItem
import com.example.noteappcompose.presentation.homeScreen.components.SearchField
import com.example.noteappcompose.presentation.homeScreen.uiStates.HomeUiEvent
import com.example.noteappcompose.presentation.theme.BottomBarContainer
import com.example.noteappcompose.presentation.theme.BottomBarIcon
import com.example.noteappcompose.presentation.theme.LightGray
import com.example.noteappcompose.presentation.theme.Orange
import com.example.noteappcompose.presentation.theme.UbuntuFont
import com.example.noteappcompose.presentation.utilities.Screen
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModel.UiEvent.ShowMessage -> scaffoldState.snackbarHostState.showSnackbar(
                    event.message
                )
            }
        }
    }
    Scaffold(
        modifier = Modifier.background(LightGray),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(containerColor = BottomBarContainer,
                contentColor = BottomBarIcon,
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        //region Add Icon
                        Icon(
                            Icons.Default.Add,
                            modifier = Modifier.size(22.sdp),
                            contentDescription = "Localized description"
                        )
                        //endregion
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Default.Image,
                            modifier = Modifier.size(22.sdp),
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            Icons.Default.Language,
                            modifier = Modifier.size(22.sdp),
                            contentDescription = "Localized description"
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(
                                Screen.NoteDetailsScreen.route + "?noteId=$CREATE_NEW_NOTE_STATE_ID"
                            )
                        },
                        containerColor = Orange,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description", tint = LightGray)
                    }
                })
        },
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .background(LightGray)
                .padding(vertical = 20.sdp, horizontal = 12.sdp)
        ) {
            Text(
                text = "My Notes",
                fontFamily = UbuntuFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.ssp,
                color = Color.White,
            )
            Spacer(Modifier.size(15.sdp))
            SearchField(
                value = viewModel.notesUiState.searchText,
                onValueChange = {
                    viewModel.onEvent(HomeUiEvent.SearchTextChanged(it))
                },
                onSearch = {
                    viewModel.onEvent(HomeUiEvent.Search)
                }
            )
            if (viewModel.notesUiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 50.sdp, start = 50.sdp).size(30.sdp))
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.sdp),
                    content = {
                        if (!viewModel.notesUiState.searchResult.isEmpty()) {
                            item() {
                                Text(
                                    text = "Search Result",
                                    modifier = Modifier.padding(top = 12.sdp),
                                    fontFamily = UbuntuFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.ssp,
                                    color = Color.White,
                                )
                            }
                            items(viewModel.notesUiState.searchResult) {
                                NoteItem(
                                    note = it,
                                    modifier = Modifier
                                        .padding(top = 12.sdp)
                                        .fillMaxWidth(),
                                    onClick = {
                                        navController.navigate(
                                            Screen.NoteDetailsScreen.route + "?noteId=${it.id}"
                                        )
                                    }
                                )
                            }
                            item() {
                                Text(
                                    text = "ALl Notes",
                                    modifier = Modifier.padding(top = 12.sdp),
                                    fontFamily = UbuntuFont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.ssp,
                                    color = Color.White,
                                )
                            }
                        }
                        items(viewModel.notesUiState.notes, key = { it.id }) {
                            NoteItem(
                                note = it,
                                modifier = Modifier
                                    .padding(top = 12.sdp)
                                    .fillMaxWidth(),
                                onClick = {
                                    navController.navigate(
                                        Screen.NoteDetailsScreen.route + "?noteId=${it.id}"
                                    )
                                }
                            )
                        }
                    }
                )
            }

        }
    }
}