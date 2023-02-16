package com.example.noteappcompose.presentation.noteDetailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.data.utilities.Constants.CREATE_NEW_NOTE_STATE_ID
import com.example.noteappcompose.domain.usecases.AddEditNoteUseCase
import com.example.noteappcompose.domain.usecases.GetNoteDetailsUseCase
import com.example.noteappcompose.domain.usecases.UploadImageUseCase
import com.example.noteappcompose.domain.usecases.ValidateNoteDescriptionUseCase
import com.example.noteappcompose.domain.usecases.ValidateNoteSubtitleUseCase
import com.example.noteappcompose.domain.usecases.ValidateNoteTitleUseCase
import com.example.noteappcompose.domain.usecases.ValidateWebLinkUseCase
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.LinkUiState
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.NoteDetailsEvent
import com.example.noteappcompose.presentation.noteDetailsScreen.uiStates.NoteDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    var addEditNoteUseCase: AddEditNoteUseCase,
    var getNoteDetailsUseCase: GetNoteDetailsUseCase,
    var validateWebLinkUseCase: ValidateWebLinkUseCase,
    var validateNoteTitleUseCase: ValidateNoteTitleUseCase,
    var validateNoteSubtitleUseCase: ValidateNoteSubtitleUseCase,
    var validateNoteDescriptionUseCase: ValidateNoteDescriptionUseCase,
    var uploadImageUseCase: UploadImageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var noteDetailsUiState by mutableStateOf(NoteDetailsUiState())
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()


    init {
        savedStateHandle.get<String>("noteId")?.let { noteId ->
            if (noteId != CREATE_NEW_NOTE_STATE_ID) {
                viewModelScope.launch {
                    try {
                        noteDetailsUiState = noteDetailsUiState.copy(isLoading = true)
                        getNoteDetailsUseCase(noteId).also { note ->
                            noteDetailsUiState = noteDetailsUiState.copy(isLoading = true)
                            val linkUiState = LinkUiState(
                                finalLink = note.webLink,
                                isLinkVisible = !note.webLink.isNullOrBlank(),
                            )
                            noteDetailsUiState = noteDetailsUiState.copy(
                                id = noteId,
                                titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                                    text = note.noteTitle
                                ),
                                subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                                    text = note.noteSubtitle
                                ),
                                descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                                    text = note.noteText
                                ),
                                dateTime = note.dateTime,
                                linkUiState = linkUiState,
                                imageLink = note.imageLink,
                                isImageVisible = !note.imageLink.isNullOrBlank(),
                                noteColor = note.noteColor,
                                isLoading = false
                            )
                        }
                    }catch (e:Exception){
                        noteDetailsUiState = noteDetailsUiState.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                    }

                }
            }
        }
    }

    fun onEvent(action: NoteDetailsEvent) {
        when (action) {
            is NoteDetailsEvent.TitleChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                        text = action.text,
                        errorMessage = null
                    )
                )

            is NoteDetailsEvent.SubtitleChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                        text = action.text,
                        errorMessage = null
                    )
                )

            is NoteDetailsEvent.NoteTextChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                        text = action.text,
                        errorMessage = null
                    )
                )

            is NoteDetailsEvent.ClickColor -> {
                noteDetailsUiState = noteDetailsUiState.copy(
                    noteColor = action.color,
                )
            }

            is NoteDetailsEvent.SaveNote -> {
                viewModelScope.launch {
                    val titleValidationResult =
                        validateNoteTitleUseCase(noteDetailsUiState.titleInputFieldUiState.text)
                    val subtitleValidationResult =
                        validateNoteSubtitleUseCase(noteDetailsUiState.subtitleInputFieldUiState.text)
                    val descriptionValidationResult =
                        validateNoteDescriptionUseCase(noteDetailsUiState.descriptionInputFieldUiState.text)
                    val hasValidationError = listOf(
                        titleValidationResult,
                        subtitleValidationResult,
                        descriptionValidationResult
                    ).any { it.error != null }
                    if (hasValidationError) {
                        noteDetailsUiState = noteDetailsUiState.copy(
                            titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                                errorMessage = titleValidationResult.error
                            ),
                            subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                                errorMessage = subtitleValidationResult.error
                            ),
                            descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                                errorMessage = descriptionValidationResult.error
                            )
                        )
                    } else {
                        try {
                            noteDetailsUiState = noteDetailsUiState.copy(isLoading = true)
                            addEditNoteUseCase(
                                id = noteDetailsUiState.id,
                                title = noteDetailsUiState.titleInputFieldUiState.text,
                                subtitle = noteDetailsUiState.subtitleInputFieldUiState.text,
                                description = noteDetailsUiState.descriptionInputFieldUiState.text,
                                imageLink = noteDetailsUiState.imageLink,
                                color = noteDetailsUiState.noteColor,
                                webLink = noteDetailsUiState.linkUiState.finalLink
                            )
                            noteDetailsUiState = noteDetailsUiState.copy(isLoading = false)
                            _eventFlow.emit(UiEvent.SaveNoteSuccess)
                        } catch (e: InvalidInputTextException) {
                            noteDetailsUiState = noteDetailsUiState.copy(
                                titleInputFieldUiState = noteDetailsUiState.titleInputFieldUiState.copy(
                                    errorMessage = validateNoteTitleUseCase(noteDetailsUiState.titleInputFieldUiState.text).error
                                ),
                                subtitleInputFieldUiState = noteDetailsUiState.subtitleInputFieldUiState.copy(
                                    errorMessage = validateNoteSubtitleUseCase(noteDetailsUiState.subtitleInputFieldUiState.text).error
                                ),
                                descriptionInputFieldUiState = noteDetailsUiState.descriptionInputFieldUiState.copy(
                                    errorMessage = validateNoteDescriptionUseCase(noteDetailsUiState.descriptionInputFieldUiState.text).error
                                ),
                            )
                            _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                            noteDetailsUiState = noteDetailsUiState.copy(isLoading = false)
                        } catch (e: Exception) {
                            noteDetailsUiState = noteDetailsUiState.copy(isLoading = false)
                            _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                        }
                    }
                }
            }

            is NoteDetailsEvent.AddUrlDialog -> {
                val webLinkValidationResult =
                    validateWebLinkUseCase(webLink = noteDetailsUiState.linkUiState.typedLink)
                if (webLinkValidationResult.error == null) {
                    noteDetailsUiState =
                        noteDetailsUiState.copy(
                            linkUiState = noteDetailsUiState.linkUiState.copy(
                                isLinkDialogVisible = false,
                                finalLink = noteDetailsUiState.linkUiState.typedLink,
                                isLinkVisible = noteDetailsUiState.linkUiState.typedLink.isNotBlank(),
                                linkError = null
                            ),
                        )
                } else {
                    noteDetailsUiState =
                        noteDetailsUiState.copy(
                            linkUiState = noteDetailsUiState.linkUiState.copy(
                                linkError = webLinkValidationResult.error
                            ),
                        )
                }
            }

            is NoteDetailsEvent.DismissUrlDialog -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        isLinkDialogVisible = false,
                        linkError = null
                    ),
                )

            is NoteDetailsEvent.UrlTextChanged -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        typedLink = action.text,
                        linkError = null,
                    ),
                )

            NoteDetailsEvent.ShowUrlDialog -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        isLinkDialogVisible = true
                    ),
                )

            NoteDetailsEvent.DeleteUrl -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    linkUiState = noteDetailsUiState.linkUiState.copy(
                        finalLink = null,
                        isLinkVisible = false
                    ),
                )

            is NoteDetailsEvent.SelectedImage -> {
                viewModelScope.launch {
                    try {
                        noteDetailsUiState = noteDetailsUiState.copy(isLoading = true)
                        var uploadImageResult = uploadImageUseCase(action.image)
                        noteDetailsUiState = noteDetailsUiState.copy(
                            isImageVisible = true,
                            imageLink = uploadImageResult,
                            isLoading = false
                        )
                    } catch (e: Exception) {
                        _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                        noteDetailsUiState = noteDetailsUiState.copy(isLoading = false)
                    }
                }

            }

            NoteDetailsEvent.DeleteImage -> noteDetailsUiState =
                noteDetailsUiState.copy(
                    isImageVisible = false,
                    imageLink = null
                )
        }
    }
    sealed class UiEvent {
        object SaveNoteSuccess : UiEvent()
        data class ShowMessage(var error: String) : UiEvent()
    }
}