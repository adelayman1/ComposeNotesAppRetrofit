package com.example.noteappcompose.domain.usecases

import android.content.Context
import android.net.Uri
import com.example.noteappcompose.domain.repositories.NoteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    var noteRepository: NoteRepository,
    @ApplicationContext var context: Context
) {
    public suspend operator fun invoke(imageUri: Uri): String {
        var imageAsByte =
            context.contentResolver.openInputStream(imageUri)?.buffered()?.use { it.readBytes() }

        val imageType = context.contentResolver.getType(imageUri)
        val extension = imageType!!.substring(imageType.indexOf("/") + 1)
        var x =  noteRepository.uploadImage(imageAsByte!!,extension)
        return x;
    }
}