package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.data.utilities.Constants.MINIMUM_TITLE_LENGTH
import com.example.noteappcompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateNoteTitleUseCase @Inject constructor(){
    operator fun invoke(title:String): ValidateResult {
        if (title.isBlank())
            return ValidateResult(error = "Please enter title")
        if (title.length<MINIMUM_TITLE_LENGTH)
            return ValidateResult(error = "Title is so short")
        return ValidateResult()
    }
}