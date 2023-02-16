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
    var noteRepository: NoteRepository,
    var validateNoteTitleUseCase: ValidateNoteTitleUseCase,
    var validateNoteSubtitleUseCase: ValidateNoteSubtitleUseCase,
    var validateNoteDescriptionUseCase: ValidateNoteDescriptionUseCase,
    var validateWebLinkUseCase: ValidateWebLinkUseCase
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
        val validateNoteTitleResult = validateNoteTitleUseCase(title)
        if (validateNoteTitleResult.isFieldDataValid()) {
            throw InvalidInputTextException(errorMsg = validateNoteTitleResult.error ?: "")
        }
        val validateNoteSubtitleResult = validateNoteSubtitleUseCase(subtitle)
        if (validateNoteTitleResult.isFieldDataValid()) {
            throw InvalidInputTextException(errorMsg = validateNoteSubtitleResult.error ?: "")
        }
        val validateNoteDescriptionResult = validateNoteDescriptionUseCase(description)
        if (validateNoteTitleResult.isFieldDataValid()) {
            throw InvalidInputTextException(errorMsg = validateNoteDescriptionResult.error ?: "")
        }
        if (webLink!=null) {
            val validateNoteWebLinkResult = validateWebLinkUseCase(webLink)
            if (validateNoteTitleResult.isFieldDataValid()) {
                throw InvalidInputTextException(errorMsg = validateNoteWebLinkResult.error ?: "")
            }
        }

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
}