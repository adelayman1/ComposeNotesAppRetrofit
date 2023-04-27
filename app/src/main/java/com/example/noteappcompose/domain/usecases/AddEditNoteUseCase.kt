package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.source.remote.requestModels.AddNoteRequestModel
import com.example.noteappcompose.data.utilities.Constants.CREATE_NEW_NOTE_STATE_ID
import com.example.noteappcompose.data.utilities.isFieldDataValid
import com.example.noteappcompose.domain.repositories.NoteRepository
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AddEditNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val validateNoteTitleUseCase: ValidateNoteTitleUseCase,
    private val validateNoteSubtitleUseCase: ValidateNoteSubtitleUseCase,
    private val validateNoteDescriptionUseCase: ValidateNoteDescriptionUseCase,
    private val validateWebLinkUseCase: ValidateWebLinkUseCase
) {
    public suspend operator fun invoke(
        id: String?,
        title: String,
        subtitle: String,
        description: String,
        imageLink: String?,
        webLink: String?,
        color: Int,
    ) {
        validateFields(title, subtitle, description, webLink)
        noteRepository.insertNote(
            Json.encodeToString(
                AddNoteRequestModel(
                    id = if (id == CREATE_NEW_NOTE_STATE_ID) null else id?.toInt(),
                    title = title,
                    subtitle = subtitle,
                    description = description,
                    image = imageLink,
                    color = color,
                    webLink = webLink
                )
            )
        )
    }

    private fun validateFields(
        title: String,
        subtitle: String,
        description: String,
        webLink: String?,
    ) {
        validateNoteTitle(title)
        validateNoteSubtitle(subtitle)
        validateNoteDescription(description)
        validateNoteWebLink(webLink)
    }

    private fun validateNoteTitle(title: String) {
        val validateNoteTitleResult = validateNoteTitleUseCase(title)
        if (!validateNoteTitleResult.isFieldDataValid()) {
            throw InvalidInputTextException(errorMsg = validateNoteTitleResult.error ?: "")
        }
    }

    private fun validateNoteSubtitle(subtitle: String) {
        val validateNoteSubtitleResult = validateNoteSubtitleUseCase(subtitle)
        if (!validateNoteSubtitleResult.isFieldDataValid()) {
            throw InvalidInputTextException(errorMsg = validateNoteSubtitleResult.error ?: "")
        }
    }

    private fun validateNoteDescription(description: String) {
        val validateNoteDescriptionResult = validateNoteDescriptionUseCase(description)
        if (!validateNoteDescriptionResult.isFieldDataValid()) {
            throw InvalidInputTextException(errorMsg = validateNoteDescriptionResult.error ?: "")
        }
    }

    private fun validateNoteWebLink(webLink: String?) {
        if (!webLink.isNullOrEmpty()) {
            val validateNoteWebLinkResult = validateWebLinkUseCase(webLink ?: "")
            if (!validateNoteWebLinkResult.isFieldDataValid()) {
                throw InvalidInputTextException(errorMsg = validateNoteWebLinkResult.error ?: "")
            }
        }
    }
}