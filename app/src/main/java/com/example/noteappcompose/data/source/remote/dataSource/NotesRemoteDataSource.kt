package com.example.noteappcompose.data.source.remote.dataSource

import com.example.noteappcompose.data.source.remote.endPoints.NotesApiService
import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.data.source.remote.responseModels.NoteResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
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