package com.example.noteappcompose.domain.usecases

import android.util.Patterns
import com.example.noteappcompose.domain.models.ValidateResult
import javax.inject.Inject

class ValidateWebLinkUseCase @Inject constructor(){
    operator fun invoke(webLink:String):ValidateResult {
        if (webLink.isBlank())
             return ValidateResult(error = "Please enter url")
        if (!Patterns.WEB_URL.matcher(webLink).matches())
            return ValidateResult(error = "Url is not valid")
        return ValidateResult()
    }
}