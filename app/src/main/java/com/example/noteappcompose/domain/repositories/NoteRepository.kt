package com.example.noteappcompose.domain.repositories

import com.example.noteappcompose.domain.models.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): List<NoteModel>
    suspend fun getNote(noteId:String): NoteModel
    suspend fun searchNotes(searchWord:String): List<NoteModel>
    suspend fun insertNote(note: String)
    suspend fun getNewNotes(): Flow<NoteModel>
    suspend fun uploadImage(imageAsByte: ByteArray, extension:String): String
}