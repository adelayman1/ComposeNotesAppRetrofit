package com.example.noteappcompose.data.utilities

import com.example.noteappcompose.data.source.remote.requestModels.RegisterRequestModel
import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.domain.models.ValidateResult
import io.ktor.client.features.ClientRequestException
import io.ktor.client.statement.readText
import io.ktor.utils.io.charsets.Charset
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import retrofit2.Response

fun<T> Response<BaseApiResponse<T>>.isDataHasGotSuccessfully() = isSuccessful && body()?.data != null && code() == 200
fun ValidateResult.isFieldDataValid() = error == null
fun<T> Response<BaseApiResponse<T>>.getErrorMessageFromResponse(): String = JSONObject(errorBody()!!.string()).getString("message")