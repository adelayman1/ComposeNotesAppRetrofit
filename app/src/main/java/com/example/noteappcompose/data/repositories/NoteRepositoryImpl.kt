package com.example.noteappcompose.data.repositories

import com.example.noteappcompose.data.source.remote.dataSource.NoteSocketDataSource
import com.example.noteappcompose.data.source.remote.dataSource.NotesRemoteDataSource
import com.example.noteappcompose.data.utilities.getErrorMessageFromResponse
import com.example.noteappcompose.data.utilities.isDataHasGotSuccessfully
import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject


class NoteRepositoryImpl @Inject constructor(
    private val notesRemoteDataSource: NotesRemoteDataSource,
    private val noteSocketDataSource: NoteSocketDataSource,
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher
) : NoteRepository {
    override suspend fun getNotes(): List<NoteModel> {
        val getNotesResult = notesRemoteDataSource.getAllNotes()
        return if (getNotesResult.isDataHasGotSuccessfully()) {
            getNotesResult.body()?.data!!.map { it.toNoteModel() }
        } else {
            throw Exception(getNotesResult.getErrorMessageFromResponse())
        }
    }

    override suspend fun getNoteDetails(noteId: String): NoteModel {
        val getNoteDetailsResult = notesRemoteDataSource.getNoteDetails(noteId)
        return if (getNoteDetailsResult.isDataHasGotSuccessfully()) {
            getNoteDetailsResult.body()!!.data!!.toNoteModel()
        } else {
            throw Exception(getNoteDetailsResult.getErrorMessageFromResponse())
        }
    }

    override suspend fun searchNotes(searchWord: String): List<NoteModel> {
        val getSearchResult = notesRemoteDataSource.searchNotes(searchWord = searchWord)
        return if (getSearchResult.isDataHasGotSuccessfully()) {
            getSearchResult.body()!!.data!!.map { it.toNoteModel() }
        } else {
            throw Exception(getSearchResult.getErrorMessageFromResponse())
        }
    }

    override suspend fun getNewNotes(): Flow<NoteModel> = withContext(ioDispatcher) {
        noteSocketDataSource.observeNotes()
    }

    override suspend fun insertNote(note: String) {
        return externalScope.launch {
            noteSocketDataSource.sendNote(note)
        }.join()
    }

    override suspend fun uploadImage(imageAsByte: ByteArray, extension: String): String {
        val uploadImageResult = notesRemoteDataSource.updateImage(
            MultipartBody.Part.createFormData(
                "image",
                "image.$extension",
                imageAsByte.toRequestBody("*/*".toMediaType())
            )
        )
        return if (uploadImageResult.isDataHasGotSuccessfully()) {
            uploadImageResult.body()!!.data!!
        } else {
            throw Exception(uploadImageResult.getErrorMessageFromResponse())
        }
    }
}