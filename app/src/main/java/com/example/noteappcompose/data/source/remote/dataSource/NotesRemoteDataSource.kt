package com.example.noteappcompose.data.source.remote.dataSource

import com.example.noteappcompose.data.source.remote.endPoints.NotesApiService
import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.data.source.remote.responseModels.NoteResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class NotesRemoteDataSource @Inject constructor(var notesApiService: NotesApiService) {
    suspend fun getAllNotes(): Response<BaseApiResponse<List<NoteResponseModel>>> =
        withContext(Dispatchers.IO) {
            notesApiService.getAllNotes()
        }

    suspend fun getAllNotesKtor(): Response<BaseApiResponse<List<NoteResponseModel>>> =
        withContext(Dispatchers.IO) {
            notesApiService.getAllNotes()
        }

    suspend fun updateImage(imageMultiPart : MultipartBody.Part): Response<BaseApiResponse<String>> =
        withContext(Dispatchers.IO) {
            notesApiService.uploadImage(imageMultiPart)
        }

    suspend fun getNoteDetails(noteId: String): Response<BaseApiResponse<NoteResponseModel>> =
        withContext(Dispatchers.IO) {
            notesApiService.getNoteDetails(noteId)
        }

    suspend fun searchNotes(searchWord: String): Response<BaseApiResponse<List<NoteResponseModel>>> =
        withContext(Dispatchers.IO) {
            notesApiService.searchNotes(searchWord)
        }
}