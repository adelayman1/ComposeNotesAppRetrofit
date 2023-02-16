package com.example.noteappcompose.data.source.remote.dataSource

import com.example.noteappcompose.data.source.remote.responseModels.NoteResponseModel
import com.example.noteappcompose.data.utilities.Constants.BASE_SOCKET_URL
import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.utilitites.CannotJoinSessionException
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteSocketDataSource @Inject constructor(var client: HttpClient) {
    private var socketSession: WebSocketSession? = null
    suspend fun joinSession(userToken: String) {
        try {
            socketSession = client.webSocketSession { url("$BASE_SOCKET_URL/notes-socket?userToken=${userToken}") }
            if (socketSession?.isActive == false) throw CannotJoinSessionException()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun sendNote(note: String) {
        try {
            socketSession?.send(Frame.Text(note))
        } catch (e: Exception) {
            throw e
        }
    }

    fun observeNotes(): Flow<NoteModel> {
        return try {
            socketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    fromTextFrameToNoteModel(it)
                } ?: flow { }
        } catch (e: Exception) {
            throw e
        }
    }
    private fun fromTextFrameToNoteModel(frame:Frame)= Json.decodeFromString<NoteResponseModel>((frame as? Frame.Text)?.readText() ?: "").toNoteModel()

    suspend fun destroySession() {
        socketSession?.close()
    }
}