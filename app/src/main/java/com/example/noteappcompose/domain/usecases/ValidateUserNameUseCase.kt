package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.utilities.Constants.MINIMUM_USER_NAME_LENGTH
import com.example.noteappcompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {
    operator fun invoke(name: String): ValidateResult {
        if (name.isBlank())
            return ValidateResult(error = "Please enter name")
        if (name.length<MINIMUM_USER_NAME_LENGTH)
            return ValidateResult(error = "Please enter valid name")
        return ValidateResult()
    }
}