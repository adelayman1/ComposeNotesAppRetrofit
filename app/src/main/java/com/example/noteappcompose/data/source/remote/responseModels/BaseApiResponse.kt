package com.example.noteappcompose.data.source.remote.responseModels

import kotlinx.serialization.Serializable

@Serializable
class BaseApiResponse<T>(var status: Boolean, var message: String, var data: T?)