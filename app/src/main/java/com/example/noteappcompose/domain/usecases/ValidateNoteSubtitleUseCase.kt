package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateNoteSubtitleUseCase @Inject constructor(){
    operator fun invoke(subtitle:String): ValidateResult {
        if (subtitle.isBlank())
            return ValidateResult(error = "Please enter subtitle")
        if (subtitle.trim().length<2)
            return ValidateResult(error = "Subtitle is so short")
        if (subtitle.contains("."))
            return ValidateResult(error = "Subtitle is not valid")
        return ValidateResult()
    }
}