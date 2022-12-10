package com.example.noteappcompose.data.source.remote.dataSource

import com.example.noteappcompose.data.source.remote.endPoints.UserApiService
import com.example.noteappcompose.data.source.remote.requestModels.LoginRequestModel
import com.example.noteappcompose.data.source.remote.requestModels.RegisterRequestModel
import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.data.source.remote.responseModels.UserResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(var userApiService: UserApiService) {

    suspend fun login(loginRequestModel: LoginRequestModel): Response<BaseApiResponse<UserResponseModel>> =
        withContext(Dispatchers.IO) {
            userApiService.login(loginBody = loginRequestModel)
        }

    suspend fun register(registerRequestModel: RegisterRequestModel): Response<BaseApiResponse<UserResponseModel>> =
        withContext(Dispatchers.IO) {
            userApiService.register(registerBody = registerRequestModel)
        }
}