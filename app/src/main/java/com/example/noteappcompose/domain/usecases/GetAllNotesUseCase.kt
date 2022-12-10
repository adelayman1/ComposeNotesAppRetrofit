package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(var noteRepository: NoteRepository) {
    suspend operator fun invoke(): Flow<List<NoteModel>> {
        return flow {
            var allNotes = noteRepository.getNotes()
            emit(allNotes)
            noteRepository.getNewNotes().collect { note ->
                val newList = allNotes.toMutableList().apply {
                    var indexOfId = indexOfFirst { it.id == note.id }
                    if (indexOfId >= 0) {
                        //exist
                        this[indexOfId] = note
                    } else {
                        add(0, note)
                    }
                }
                allNotes = newList
                emit(newList)
            }
        }
    }
}