package com.example.noteappcompose.presentation.noteDetailsScreen

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.noteappcompose.presentation.noteDetailsScreen.components.BottomSheetItem
import com.example.noteappcompose.presentation.noteDetailsScreen.components.ColorBox
import com.example.noteappcompose.presentation.noteDetailsScreen.components.NoteInputField
import com.example.noteappcompose.presentation.noteDetailsScreen.components.ToolBarIconButton
import com.example.noteappcompose.presentation.noteDetailsScreen.components.UrlDialog
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.NoteDetailsEvent
import com.example.noteappcompose.presentation.theme.BottomBarIcon
import com.example.noteappcompose.presentation.theme.BottomSheet
import com.example.noteappcompose.presentation.theme.LightGray
import com.example.noteappcompose.presentation.theme.Orange
import com.example.noteappcompose.presentation.theme.Red
import com.example.noteappcompose.presentation.theme.SubtitleGray
import com.example.noteappcompose.presentation.theme.UbuntuFont
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun NoteDetailsScreen(
    viewModel: NoteDetailsViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    )
    val imagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                viewModel.onEvent(NoteDetailsEvent.SelectedImage(uri))
            }
//                viewModel.onEvent(NoteDetailsEvent.SelectedImage(uri))
        }
    )
    val bottomState = BottomSheetState(BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomState)
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                NoteDetailsViewModel.UiEvent.SaveNoteSuccess -> navController.navigateUp()
                is NoteDetailsViewModel.UiEvent.ShowMessage -> scaffoldState.snackbarHostState.showSnackbar(
                    event.error
                )
            }
        }
    }
    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 195.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .height(40.sdp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "properties",
                        fontFamily = UbuntuFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = 13.ssp,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 10.sdp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    viewModel.noteDetailsUiState.noteColors.forEach {
                        val colorAsInt = it.toArgb()
                        Spacer(modifier = Modifier.size(10.sdp))
                        ColorBox(
                            color = it,
                            onClick = {
                                viewModel.onEvent(NoteDetailsEvent.ClickColor(colorAsInt))
                            },
                            isCheckIconVisible = viewModel.noteDetailsUiState.noteColor == colorAsInt
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Pick Color",
                        modifier = Modifier.padding(horizontal = 5.sdp),
                        fontFamily = UbuntuFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = 13.ssp,
                    )
                }
                Spacer(modifier = Modifier.size(20.sdp))
                BottomSheetItem(icon = Icons.Filled.Image, text = "Add Image", onClick = {
                    val permissionState = permissionsState.permissions.get(0)
                    when {
                        permissionState.hasPermission -> {
                            Log.d("aaaaaaaaaaaaaaaa", "kkjjkjjkj1")
                            imagePermissionLauncher.launch(
                                arrayOf(
                                    "image/png",
                                    "image/jpg",
                                    "image/jpeg"
                                )
                            )
                        }

                        permissionState.shouldShowRationale -> {
                            Log.d("aaaaaaaaaaaaaaaa", "kkjjkjjkj2")
                            permissionsState.launchMultiplePermissionRequest()
                        }

                        !permissionState.shouldShowRationale && !permissionState.hasPermission -> {
                            Log.d("aaaaaaaaaaaaaaaa", "kkjjkjjkj3")
//                            TODO("SNAKBAR")
//                            Text(
//                                text = "Camera permission was permanently" +
//                                        "denied. You can enable it in the app" +
//                                        "settings."
//                            )
                        }
                    }
                })
                Spacer(modifier = Modifier.size(7.sdp))

                BottomSheetItem(
                    icon = Icons.Filled.Language,
                    text = "Add URL",
                    onClick = { viewModel.onEvent(NoteDetailsEvent.ShowUrlDialog) }
                )
                Spacer(modifier = Modifier.size(7.sdp))
            }
        },
        modifier = Modifier.background(LightGray),
        backgroundColor = LightGray,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 40.sdp,
        sheetShape = RoundedCornerShape(topStart = 15.sdp, topEnd = 15.sdp),
        sheetBackgroundColor = BottomSheet
    ) {
        if (viewModel.noteDetailsUiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 50.sdp, start = 50.sdp).size(30.sdp))
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(LightGray)
                    .padding(vertical = 11.sdp, horizontal = 0.sdp),
            ) {
                if (viewModel.noteDetailsUiState.linkUiState.isLinkDialogVisible) {
                    UrlDialog(
                        text = viewModel.noteDetailsUiState.linkUiState.typedLink,
                        onTextChange = { viewModel.onEvent(NoteDetailsEvent.UrlTextChanged(it)) },
                        onClickAdd = { viewModel.onEvent(NoteDetailsEvent.AddUrlDialog) },
                        onClickDismiss = { viewModel.onEvent(NoteDetailsEvent.DismissUrlDialog) },
                        error = viewModel.noteDetailsUiState.linkUiState.linkError,
                        isErrorVisible = viewModel.noteDetailsUiState.linkUiState.linkError != null
                    )
                }
                Row(modifier = Modifier.padding(start = 5.sdp, end = 5.sdp)) {
                    ToolBarIconButton(
                        icon = Icons.Default.ArrowBackIos,
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.size(20.sdp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ToolBarIconButton(
                        icon = Icons.Default.Done,
                        onClick = { viewModel.onEvent(NoteDetailsEvent.SaveNote) },
                        modifier = Modifier.size(20.sdp)
                    )
                }
                NoteInputField(
                    text = viewModel.noteDetailsUiState.titleInputFieldUiState.text,
                    hint = "Note title",
                    error = viewModel.noteDetailsUiState.titleInputFieldUiState.errorMessage,
                    isErrorVisible = viewModel.noteDetailsUiState.titleInputFieldUiState.errorMessage != null,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        viewModel.onEvent(NoteDetailsEvent.TitleChanged(it))
                    },
                    textStyle = TextStyle(
                        fontFamily = UbuntuFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.ssp,
                    )
                )
                Text(
                    text = viewModel.noteDetailsUiState.dateTime,
                    modifier = Modifier
                        .padding(start = 13.sdp)
                        .fillMaxWidth(),
                    fontFamily = UbuntuFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.ssp,
                    color = BottomBarIcon
                )
                Spacer(modifier = Modifier.size(8.sdp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 13.sdp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.sdp)
                            .width(width = 5.sdp)
                            .height(35.sdp)
                            .clip(RoundedCornerShape(10.sdp))
                            .background(Color(viewModel.noteDetailsUiState.noteColor))
                    )
                    NoteInputField(
                        text = viewModel.noteDetailsUiState.subtitleInputFieldUiState.text,
                        hint = "Note subtitle",
                        modifier = Modifier.fillMaxWidth(),
                        error = viewModel.noteDetailsUiState.subtitleInputFieldUiState.errorMessage,
                        isErrorVisible = viewModel.noteDetailsUiState.subtitleInputFieldUiState.errorMessage != null,
                        onValueChange = {
                            viewModel.onEvent(NoteDetailsEvent.SubtitleChanged(it))
                        },
                        textStyle = TextStyle(
                            fontFamily = UbuntuFont,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.ssp,
                        ),
                        textColor = SubtitleGray
                    )
                }
                Spacer(modifier = Modifier.size(5.sdp))
                AnimatedVisibility(visible = viewModel.noteDetailsUiState.linkUiState.isLinkVisible) {
                    Row(
                        modifier = Modifier.padding(start = 12.sdp, end = 12.sdp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.noteDetailsUiState.linkUiState.finalLink ?: "",
                            modifier = Modifier.weight(1f),
                            fontFamily = UbuntuFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.ssp,
                            color = Orange,
                            textDecoration = TextDecoration.Underline
                        )
                        IconButton(onClick = { viewModel.onEvent(NoteDetailsEvent.DeleteUrl) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "delete url",
                                modifier = Modifier.size(20.sdp),
                                tint = Red
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    viewModel.noteDetailsUiState.isImageVisible,
                    enter = slideInHorizontally()
                ) {
                    Column {
                        Spacer(modifier = Modifier.size(5.sdp))
                        Box(
                            contentAlignment = Alignment.TopEnd,
                            modifier = Modifier.padding(horizontal = 15.sdp)
                        ) {
                            if (viewModel.noteDetailsUiState.imageLink == null) {
                                CircularProgressIndicator()
                            } else {
                                SubcomposeAsyncImage(
                                    model = viewModel.noteDetailsUiState.imageLink ?: "",
                                    loading = {
                                        CircularProgressIndicator()
                                    },
                                    contentDescription = null
                                )
                                IconButton(
                                    onClick = { viewModel.onEvent(NoteDetailsEvent.DeleteImage) },
                                    Modifier
                                        .padding(top = 10.sdp, end = 10.sdp)
                                        .size(25.sdp)
                                        .clip(CircleShape)
                                        .background(Red)
                                        .padding(4.sdp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
                NoteInputField(
                    text = viewModel.noteDetailsUiState.descriptionInputFieldUiState.text,
                    hint = "Type note here",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    error = viewModel.noteDetailsUiState.descriptionInputFieldUiState.errorMessage,
                    isErrorVisible = viewModel.noteDetailsUiState.descriptionInputFieldUiState.errorMessage != null,
                    onValueChange = {
                        viewModel.onEvent(NoteDetailsEvent.NoteTextChanged(it))
                    },
                    singleLine = false,
                    textStyle = TextStyle(
                        fontFamily = UbuntuFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.ssp,
                    )
                )
            }
        }
    }
}