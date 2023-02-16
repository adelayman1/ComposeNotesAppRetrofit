package com.example.noteappcompose.data.source.remote.endPoints

import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.data.source.remote.responseModels.NoteResponseModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface NotesApiService {
    @POST("notes/image")
    @Multipart()
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<BaseApiResponse<String>>

    @GET("notes")
    suspend fun getAllNotes(): Response<BaseApiResponse<List<NoteResponseModel>>>

    @GET("notes/search")
    suspend fun searchNotes(@Query("search_word") searchWord:String): Response<BaseApiResponse<List<NoteResponseModel>>>

    @GET("notes/{note_id}")
    suspend fun getNoteDetails(@Path("note_id") noteId: String): Response<BaseApiResponse<NoteResponseModel>>
}