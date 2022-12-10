package com.example.noteappcompose.data.repositories

import com.example.noteappcompose.data.source.remote.dataSource.NoteSocketDataSource
import com.example.noteappcompose.data.source.remote.dataSource.NotesRemoteDataSource
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
        var getNotesResult = notesRemoteDataSource.getAllNotes()
        return if (getNotesResult.isSuccessful && getNotesResult.body()?.data != null && getNotesResult.code() == 200) {
            getNotesResult.body()?.data!!.map { it.toNoteModel() }
//            getNotesResult.data!!.associateBy({ it.noteId }, { it.toNoteModel() })
        } else {
            val errorBody = JSONObject(getNotesResult.errorBody()!!.string())
            throw Exception(errorBody.getString("message"))
        }
    }

    override suspend fun getNote(noteId: String): NoteModel {
        var getNoteDetailsResult = notesRemoteDataSource.getNoteDetails(noteId)
        return if (getNoteDetailsResult.isSuccessful && getNoteDetailsResult.body()?.data != null && getNoteDetailsResult.code() == 200) {
            getNoteDetailsResult.body()!!.data!!.toNoteModel()
        } else{
            val errorBody = JSONObject(getNoteDetailsResult.errorBody()!!.string())
            throw Exception(errorBody.getString("message"))
        }
    }

    override suspend fun searchNotes(searchWord: String): List<NoteModel> {
        var getSearchResult = notesRemoteDataSource.searchNotes(searchWord = searchWord)
        return if (getSearchResult.isSuccessful && getSearchResult.body()?.data != null && getSearchResult.code() == 200) {
            getSearchResult.body()!!.data!!.map { it.toNoteModel() }
        } else{
            val errorBody = JSONObject(getSearchResult.errorBody()!!.string())
            throw Exception(errorBody.getString("message"))
        }
    }

    override suspend fun getNewNotes(): Flow<NoteModel> = withContext(ioDispatcher) {
        noteSocketDataSource.observeNotes()
    }

    override suspend fun insertNote(note: String) {
        return externalScope.launch {
            noteSocketDataSource.sendNote(
                note
            )
        }.join()
    }

    override suspend fun uploadImage(imageAsByte: ByteArray, extension: String): String {
        var uploadImageResult = notesRemoteDataSource.updateImage(
            MultipartBody.Part.createFormData(
                "image",
                "image.$extension",
                imageAsByte.toRequestBody("*/*".toMediaType())
            )
        )
        return if (uploadImageResult.isSuccessful && uploadImageResult.body()?.data != null && uploadImageResult.code() == 200) {
            uploadImageResult.body()!!.data!!
        } else {
            val errorBody = JSONObject(uploadImageResult.errorBody()!!.string())
            throw Exception(errorBody.getString("message"))

        }
    }
}