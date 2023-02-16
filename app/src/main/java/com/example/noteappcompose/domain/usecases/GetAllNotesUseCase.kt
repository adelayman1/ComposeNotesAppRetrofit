package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(var noteRepository: NoteRepository) {
    private var allNotes: List<NoteModel> = emptyList()
    suspend operator fun invoke(): Flow<List<NoteModel>> {
        return flow {
            allNotes = noteRepository.getNotes()
            emit(allNotes)
            noteRepository.getNewNotes().collect { newNote ->
                val newNotesList = addNewNoteToOldNotesList(newNote)
                allNotes = newNotesList
                emit(newNotesList)
            }
        }
    }
    private fun addNewNoteToOldNotesList(newNote: NoteModel): List<NoteModel> {
        return allNotes.toMutableList().apply {
            val indexOfNoteWithSameId = indexOfFirst { it.id == newNote.id }
            if (isNoteInList(indexOfNoteWithSameId)) {
                this[indexOfNoteWithSameId] = newNote
            } else {
                add(0, newNote)
            }
        }
    }
    private fun isNoteInList(indexOfNoteId: Int) = indexOfNoteId >= 0
}