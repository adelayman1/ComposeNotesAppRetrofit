package com.example.noteappcompose.domain.usecases

import com.example.noteappcompose.domain.models.NoteModel
import com.example.noteappcompose.domain.models.UserModel
import com.example.noteappcompose.domain.repositories.NoteRepository
import com.example.noteappcompose.domain.repositories.UserRepository
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import com.example.noteappcompose.domain.utilitites.UserLoggedInException
import com.example.noteappcompose.domain.utilitites.UserNotLoggedInException
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
    private val userRepository: UserRepository,
) {
    public suspend operator fun invoke(searchWord: String): List<NoteModel> {
        if (userRepository.getUserToken().isNullOrBlank())
            throw UserNotLoggedInException()
         if (searchWord.isBlank())
            throw InvalidInputTextException("Invalid search word")
         return noteRepository.searchNotes(searchWord)
    }
}