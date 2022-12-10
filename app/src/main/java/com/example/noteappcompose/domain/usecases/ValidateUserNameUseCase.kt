package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {
    operator fun invoke(name: String): ValidateResult {
        if (name.isBlank())
            return ValidateResult(error = "Please enter name")
        if (name.length<3)
            return ValidateResult(error = "Please enter valid name")
        return ValidateResult()
    }
}