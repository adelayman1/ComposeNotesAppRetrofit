package com.example.noteappcompose.domain.utilitites

class InvalidNoteIdException: Exception("ID is not valid")
class InvalidInputTextException(errorMsg:String): Exception(errorMsg)
class UserLoggedInException: Exception("User Is Already Logged In")
class UserNotLoggedInException: Exception("User Is Not Logged In")
class CannotJoinSessionException: Exception("can't connect to socket")