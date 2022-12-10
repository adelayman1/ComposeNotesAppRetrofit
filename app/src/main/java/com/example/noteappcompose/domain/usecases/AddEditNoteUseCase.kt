package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.source.remote.requestModels.AddNoteRequestModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import com.example.noteappcompose.domain.utilitites.InvalidNoteSubtitleException
import com.example.noteappcompose.domain.utilitites.InvalidNoteTextException
import com.example.noteappcompose.domain.utilitites.InvalidNoteTitleException
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
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
        if (validateNoteTitleResult.error !=null) {
            throw InvalidInputTextException(errorMsg = validateNoteTitleResult.error?:"")
        }
        val validateNoteSubtitleResult = validateNoteSubtitleUseCase(subtitle)
        if (validateNoteTitleResult.error !=null) {
            throw InvalidInputTextException(errorMsg = validateNoteSubtitleResult.error?:"")
        }
        val validateNoteDescriptionResult = validateNoteDescriptionUseCase(description)
        if (validateNoteTitleResult.error !=null) {
            throw InvalidInputTextException(errorMsg = validateNoteDescriptionResult.error?:"")
        }
        if(webLink.isNullOrEmpty()){
            val validateNoteWebLinkResult = validateWebLinkUseCase(webLink?:"")
            if (validateNoteTitleResult.error !=null) {
                throw InvalidInputTextException(errorMsg = validateNoteWebLinkResult.error?:"")
            }
        }

        noteRepository.insertNote(
            Json.encodeToString(
                AddNoteRequestModel(
                    id = if (id == "-1") null else id?.toInt(),
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