package com.example.noteappcompose

import android.app.Application
import com.example.noteappcompose.data.source.remote.dataSource.NoteSocketDataSource
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class NoteApp : Application()