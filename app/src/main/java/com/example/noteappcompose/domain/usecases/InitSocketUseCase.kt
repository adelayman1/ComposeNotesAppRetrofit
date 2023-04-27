package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.source.remote.dataSource.NoteSocketDataSource
import com.example.noteappcompose.domain.repositories.UserRepository
import com.example.noteappcompose.domain.utilitites.UserLoggedInException
import com.example.noteappcompose.domain.utilitites.UserNotLoggedInException
import javax.inject.Inject

class InitSocketUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val socketDataSource: NoteSocketDataSource
) {
    public suspend operator fun invoke() {
        if (userRepository.getUserToken().isNullOrBlank())
            throw UserNotLoggedInException()
        socketDataSource.joinSession(userRepository.getUserToken()!!)
    }
}