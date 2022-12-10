package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.repositories.UserRepository
import com.example.noteappcompose.domain.utilitites.InvalidNoteIdException
import javax.inject.Inject

class IsUserSplashUseCase @Inject constructor(var userRepository: UserRepository) {
    public suspend operator fun invoke(): Boolean {
       return userRepository.getUserToken().isNullOrBlank()
    }
}
//TODO(MUTEX)