package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.utilities.isFieldDataValid
import com.example.noteappcompose.domain.models.UserModel
import com.example.noteappcompose.domain.repositories.UserRepository
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import com.example.noteappcompose.domain.utilitites.UserLoggedInException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    public suspend operator fun invoke(name: String, email: String, password: String): UserModel {
        if (!userRepository.getUserToken().isNullOrBlank())
            throw UserLoggedInException()
        validateFields(name, email, password)
        return userRepository.register(email, name, password)
    }

    private fun validateFields(userName: String, email: String, password: String) {
        validateUserName(userName)
        validateEmail(email)
        validatePassword(password)
    }

    private fun validateUserName(userName: String) {
        val validateUserNameResult = validateUserNameUseCase(userName)
        if (!validateUserNameResult.isFieldDataValid()) throw InvalidInputTextException(
            validateUserNameResult.error ?: ""
        )
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