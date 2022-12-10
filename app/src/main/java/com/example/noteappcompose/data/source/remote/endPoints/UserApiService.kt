package com.example.noteappcompose.data.source.remote.endPoints

import com.example.noteappcompose.data.source.remote.requestModels.LoginRequestModel
import com.example.noteappcompose.data.source.remote.requestModels.RegisterRequestModel
import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.data.source.remote.responseModels.UserResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiService {
    @POST("user/login")
    @Headers("No-Authentication:true")
    suspend fun login(@Body loginBody: LoginRequestModel): Response<BaseApiResponse<UserResponseModel>>

    @POST("user/register")
    @Headers("No-Authentication:true")
    suspend fun register(@Body registerBody: RegisterRequestModel): Response<BaseApiResponse<UserResponseModel>>
}