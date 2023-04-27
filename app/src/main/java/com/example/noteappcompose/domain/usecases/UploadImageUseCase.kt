package com.example.noteappcompose.domain.usecases

import android.content.Context
import android.net.Uri
import com.example.noteappcompose.domain.repositories.NoteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    @ApplicationContext private val context: Context
) {
    public suspend operator fun invoke(imageUri: Uri): String {
        val imageAsByte = convertImageFromUriToByte(imageUri)
        val extension = getImageExtensionByUri(imageUri)
        val uploadImageResult = noteRepository.uploadImage(imageAsByte!!, extension)
        return uploadImageResult;
    }

    private fun convertImageFromUriToByte(imageUri: Uri) =
        context.contentResolver.openInputStream(imageUri)?.buffered()?.use { it.readBytes() }

    private fun getImageTypeByUri(imageUri: Uri) = context.contentResolver.getType(imageUri)
    private fun getImageExtensionByUri(imageUri: Uri): String {
        val imageType = getImageTypeByUri(imageUri)
        val imageExtension = imageType!!.substring(imageType.indexOf("/") + 1)
        return imageExtension
    }
}
