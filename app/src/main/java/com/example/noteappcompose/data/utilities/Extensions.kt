package com.example.noteappcompose.data.utilities

import com.example.noteappcompose.data.source.remote.responseModels.BaseApiResponse
import com.example.noteappcompose.domain.models.ValidateResult
import org.json.JSONObject
import retrofit2.Response

fun<T> Response<BaseApiResponse<T>>.isDataHasGotSuccessfully() = isSuccessful && body()?.data != null && code() == 200
fun ValidateResult.isFieldDataValid() = error == null
fun<T> Response<BaseApiResponse<T>>.getErrorMessageFromResponse(): String = JSONObject(errorBody()!!.string()).getString("message")