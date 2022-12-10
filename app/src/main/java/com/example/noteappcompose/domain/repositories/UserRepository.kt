package com.example.noteappcompose.domain.repositories

import com.example.noteappcompose.domain.models.UserModel

interface UserRepository {
    suspend fun login(email:String,password:String): UserModel

    suspend fun register(email:String,name:String,password:String): UserModel

    suspend fun getUserToken(): String?
}