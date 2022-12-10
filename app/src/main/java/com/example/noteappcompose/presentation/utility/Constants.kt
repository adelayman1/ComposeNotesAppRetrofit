package com.example.noteappcompose.presentation.utility

import androidx.compose.ui.graphics.Color
import com.example.noteappcompose.presentation.theme.Blue
import com.example.noteappcompose.presentation.theme.Orange
import com.example.noteappcompose.presentation.theme.Red
import com.example.noteappcompose.presentation.theme.SearchGray
import javax.inject.Singleton

@Singleton
object Constants {
        val noteColorsList = listOf(SearchGray, Orange, Red, Blue, Color.Black)
}