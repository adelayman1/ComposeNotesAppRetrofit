package com.example.noteappcompose.data.source.local.dataSource

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(var prefs: SharedPreferences) {
    suspend fun saveUserToken(authorizationKey: String) = withContext(Dispatchers.IO) {
        val editor = prefs.edit()
        editor.putString("UserToken", authorizationKey)
        return@withContext editor.commit()
    }

    suspend fun getUserToken(): String? =
        withContext(Dispatchers.IO) {
            prefs.getString("UserToken", null)
        }

    suspend fun deleteUserToken() =
        withContext(Dispatchers.IO) {
            val editor = prefs.edit()
            editor.remove("UserToken")
            return@withContext editor.commit()
        }
}