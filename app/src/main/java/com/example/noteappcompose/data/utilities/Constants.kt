package com.example.noteappcompose.data.utilities

object Constants {
    const val BASE_HTTP_URL = "http://192.168.1.6:4040/"
    const val BASE_SOCKET_URL = "ws://192.168.1.6:4040"
    const val USER_TOKEN_SHARED_PREFERENCES_KEY:String = "UserToken"
    const val USER_SHARED_PREFERENCES_KEY:String = "user"
    const val MINIMUM_USER_NAME_LENGTH:Int = 3
    const val MINIMUM_TITLE_LENGTH:Int = 2
    const val CREATE_NEW_NOTE_STATE_ID:String = "-1"
    const val MINIMUM_SUBTITLE_LENGTH:Int = 2
    const val MINIMUM_DESCRIPTION_LENGTH:Int = 2
    val VALID_PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}\$".toRegex()
}