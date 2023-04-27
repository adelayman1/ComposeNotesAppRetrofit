package com.example.noteappcompose.data.source.remote.requestModels

import kotlinx.serialization.Serializable

@Serializable
data class AddNoteRequestModel(
    val id:Int?=null,
    val title: String,
    val subtitle: String,
    val description:String,
    val image:String?,
    val webLink:String?,
    val color: Int
)