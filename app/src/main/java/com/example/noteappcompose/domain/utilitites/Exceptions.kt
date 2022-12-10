package com.example.noteappcompose.domain.utilitites

class InvalidNoteTitleException(): Exception("Title is not valid")
class InvalidNoteSubtitleException(): Exception("Subtitle is not valid")
class InvalidNoteTextException: Exception("Text is not valid")
class InvalidNoteIdException: Exception("ID is not valid")
class InvalidInputTextException(errorMsg:String): Exception(errorMsg)
class UserLoggedInException: Exception("User Is Already Logged In")
class UserNotLoggedInException: Exception("User Is Not Logged In")