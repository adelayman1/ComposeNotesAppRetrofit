package com.example.noteappcompose.data.repositories

import com.example.noteappcompose.data.source.local.dataSource.UserLocalDataSource
import com.example.noteappcompose.data.source.remote.dataSource.UserRemoteDataSource
import com.example.noteappcompose.data.source.remote.requestModels.LoginRequestModel
import com.example.noteappcompose.data.source.remote.requestModels.RegisterRequestModel
import com.example.noteappcompose.data.utilities.isDataHasGotSuccessfully
import com.example.noteappcompose.domain.models.UserModel
import com.example.noteappcompose.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    var userRemoteDataSource: UserRemoteDataSource,
    var userLocalDataSource: UserLocalDataSource,
    val externalScope: CoroutineScope
) : UserRepository {
    override suspend fun login(email: String, password: String): UserModel {
        return externalScope.async {
            userRemoteDataSource.login(LoginRequestModel(email, password))
                .also {
                    if (it.isDataHasGotSuccessfully()) {
                        userLocalDataSource.saveUserToken(it.body()!!.data!!.userToken)
                    } else throw Exception(it.body()!!.message)
                }
        }.await().body()!!.data!!.toUserModel()
    }

    override suspend fun register(email: String, name: String, password: String): UserModel {
        return externalScope.async {
            userRemoteDataSource.register(RegisterRequestModel(name, email, password))
                .also {
                    if (it.isDataHasGotSuccessfully()) {
                        userLocalDataSource.saveUserToken(it.body()!!.data!!.userToken)
                    } else throw Exception(it.body()?.message)
                }
        }.await().body()!!.data!!.toUserModel()
    }

    override suspend fun getUserToken(): String? {
        return userLocalDataSource.getUserToken()
    }
}