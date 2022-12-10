package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.UserModel
import com.example.noteappcompose.domain.repositories.UserRepository
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import com.example.noteappcompose.domain.utilitites.UserLoggedInException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    public suspend operator fun invoke(email: String, password: String):UserModel {
        if (!userRepository.getUserToken().isNullOrBlank())
            throw UserLoggedInException()

        val validateEmailResult = validateEmailUseCase(email)
        if (validateEmailResult.error != null)
            throw InvalidInputTextException(validateEmailResult.error ?: "")
        val validatePasswordResult = validatePasswordUseCase(password)
        if (validatePasswordResult.error != null)
            throw InvalidInputTextException(validatePasswordResult.error!!)
        return userRepository.login(email, password)
    }
}