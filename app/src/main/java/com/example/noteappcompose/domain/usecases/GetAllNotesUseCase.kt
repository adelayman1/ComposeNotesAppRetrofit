package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val initSocketUseCase: InitSocketUseCase
) {
    private var allNotes: List<NoteModel> = emptyList()
    suspend operator fun invoke(): Flow<List<NoteModel>> {
        initSocketUseCase.invoke()
        return flow {
            allNotes = noteRepository.getNotes()
            emit(allNotes)
            noteRepository.getNewNotes().collect { newNote ->
                val newNotesList = mergeNewNoteToNotesList(newNote)
                allNotes = newNotesList
                emit(newNotesList)
            }
        }
    }

    private fun mergeNewNoteToNotesList(newNote: NoteModel): List<NoteModel> {
        return if (isNoteAlreadyExists(newNote.id)) {
            editNoteInList(newNote)
        } else {
            addNoteInFirstListPosition(newNote)
        }
    }

    private fun editNoteInList(note: NoteModel): List<NoteModel> {
        return allNotes.toMutableList().apply {
            this[getNoteIndexById(note.id)] = note
        }
    }

    private fun addNoteInFirstListPosition(newNote: NoteModel): List<NoteModel> {
        return allNotes.toMutableList().apply {
            add(0, newNote)
        }
    }

    private fun isNoteAlreadyExists(noteId: String): Boolean {
        return isIndexInList(getNoteIndexById(noteId))
    }

    private fun getNoteIndexById(noteId: String): Int {
        return allNotes.indexOfFirst { it.id == noteId }
    }

    private fun isIndexInList(indexOfNoteId: Int) = indexOfNoteId >= 0
}
