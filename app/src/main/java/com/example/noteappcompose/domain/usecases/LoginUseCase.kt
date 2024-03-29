package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.utilities.isFieldDataValid
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
    public suspend operator fun invoke(email: String, password: String): UserModel {
        if (!userRepository.getUserToken().isNullOrBlank())
            throw UserLoggedInException()
        validateFields(email, password)
        return userRepository.login(email, password)
    }

    private fun validateFields(email: String, password: String) {
        validateEmail(email)
        validatePassword(password)
    }

    private fun validateEmail(email: String) {
        val validateEmailResult = validateEmailUseCase(email)
        if (!validateEmailResult.isFieldDataValid()) throw InvalidInputTextException(
            validateEmailResult.error ?: ""
        )
    }

    private fun validatePassword(password: String) {
        val validatePasswordResult = validatePasswordUseCase(password)
        if (!validatePasswordResult.isFieldDataValid()) throw InvalidInputTextException(
            validatePasswordResult.error ?: ""
        )
    }
}