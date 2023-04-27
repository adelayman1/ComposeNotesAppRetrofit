package com.example.noteappcompose.presentation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.data.source.remote.dataSource.NoteSocketDataSource
import com.example.noteappcompose.domain.usecases.GetAllNotesUseCase
import com.example.noteappcompose.domain.usecases.SearchUseCase
import com.example.noteappcompose.presentation.homeScreen.uiStates.HomeUiEvent
import com.example.noteappcompose.presentation.homeScreen.uiStates.NoteItemUiState
import com.example.noteappcompose.presentation.homeScreen.uiStates.NotesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    var notesUiState by mutableStateOf(NotesUiState(isLoading = true))

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            try {
                notesUiState = notesUiState.copy(
                    isLoading = true
                )
                getAllNotesUseCase.invoke().collect {
                    val newNotesList = it.map { note ->
                        NoteItemUiState(
                            id = note.id,
                            noteTitle = note.noteTitle,
                            noteSubtitle = note.noteSubtitle,
                            noteDate = note.dateTime,
                            noteColor = note.noteColor,
                            imageLink = note.imageLink,
                            isImageVisible = !note.imageLink.isNullOrBlank()
                        )
                    }
                    notesUiState = notesUiState.copy(
                        notes = newNotesList,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                notesUiState = notesUiState.copy(
                    isLoading = false
                )
            }
        }
    }

    fun search() {
        viewModelScope.launch {
            if (notesUiState.searchText.trim().isBlank())
                notesUiState = notesUiState.copy(searchResult = emptyList())
            else {
                try {
                    notesUiState = notesUiState.copy(
                        isLoading = true
                    )
                    val searchResult = searchUseCase(notesUiState.searchText)
                    val newSearchedList = searchResult.map { note ->
                        NoteItemUiState(
                            id = note.id,
                            noteTitle = note.noteTitle,
                            noteSubtitle = note.noteSubtitle,
                            noteDate = note.dateTime,
                            noteColor = note.noteColor,
                            imageLink = note.imageLink,
                            isImageVisible = !note.imageLink.isNullOrBlank()
                        )
                    }
                    notesUiState = notesUiState.copy(searchResult = newSearchedList ,  isLoading = false)
                } catch (e: Exception) {
                    _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                    notesUiState = notesUiState.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onEvent(action: HomeUiEvent) {
        when (action) {
            is HomeUiEvent.SearchTextChanged -> notesUiState =
                notesUiState.copy(searchText = action.text)

            HomeUiEvent.Search -> search()
        }
    }

    sealed class UiEvent {
        data class ShowMessage(var message: String) : UiEvent()
    }
}