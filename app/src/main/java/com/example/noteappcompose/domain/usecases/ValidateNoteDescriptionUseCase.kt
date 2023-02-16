package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.utilities.Constants.MINIMUM_DESCRIPTION_LENGTH
import com.example.noteappcompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateNoteDescriptionUseCase @Inject constructor() {
    operator fun invoke(description: String): ValidateResult {
        if (description.isBlank())
            return ValidateResult(error = "Please enter description")
        if (description.trim().length < MINIMUM_DESCRIPTION_LENGTH)
            return ValidateResult(error = "Description is so short")
        return ValidateResult()
    }
}