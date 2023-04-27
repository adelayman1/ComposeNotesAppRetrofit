package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import com.example.noteappcompose.domain.utilitites.InvalidNoteIdException
import javax.inject.Inject

class GetNoteDetailsUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    public suspend operator fun invoke(noteId: String): NoteModel {
        if (!isNoteIdValid(noteId)) {
            throw InvalidNoteIdException()
        }
        return noteRepository.getNoteDetails(noteId) ?: throw InvalidNoteIdException()
    }

    private fun isNoteIdValid(noteId: String) = noteId.toInt() >= 0
}
