package com.example.noteappcompose.domain.usecases

import android.util.Patterns
import com.example.noteappcompose.domain.models.ValidateResult
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): ValidateResult {
        if (password.isBlank())
            return ValidateResult(error = "Please enter password")
        if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[^A-Za-z0-9]).{8,}\$".toRegex()))
            return ValidateResult(error = "Please enter valid password")
        return ValidateResult()
    }
}