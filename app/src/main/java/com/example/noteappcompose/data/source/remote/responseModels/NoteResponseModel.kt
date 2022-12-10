package com.example.noteappcompose.data.source.remote.responseModels

import com.example.noteappcompose.domain.models.NoteModel
import kotlinx.serialization.Serializable

@Serializable
data class NoteResponseModel(
    val noteId: String,
    val userToken: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val date: String,
    val color: Int,
    val image: String?,
    val webLink: String?
) {
    fun toNoteModel() = NoteModel(
        id = noteId,
        noteTitle = title,
        noteSubtitle = subtitle,
        noteText = description,
        dateTime = date,
        noteColor = color,
        imageLink = image,
        webLink = webLink
    )
}