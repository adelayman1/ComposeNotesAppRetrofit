package com.example.noteappcompose.presentation.registerScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.domain.usecases.RegisterUseCase
import com.example.noteappcompose.domain.usecases.ValidateEmailUseCase
import com.example.noteappcompose.domain.usecases.ValidateUserNameUseCase
import com.example.noteappcompose.domain.usecases.ValidatePasswordUseCase
import com.example.noteappcompose.domain.utilitites.InvalidInputTextException
import com.example.noteappcompose.presentation.registerScreen.uiStates.RegisterUiEvent
import com.example.noteappcompose.presentation.registerScreen.uiStates.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    var validateEmailUseCase: ValidateEmailUseCase,
    var validatePasswordUseCase: ValidatePasswordUseCase,
    var validateUserNameUseCase: ValidateUserNameUseCase,
    var registerUseCase: RegisterUseCase
) : ViewModel() {
    var registerUiState by mutableStateOf(RegisterUiState(isLoading = false))
        private set;

    private var _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun register() {
        viewModelScope.launch {
            registerUiState = registerUiState.copy(isLoading = true)
            val nameValidation =
                validateUserNameUseCase(registerUiState.nameUiState.text)
            val emailValidationResult =
                validateEmailUseCase(registerUiState.emailUiState.text)
            val passwordValidationResult =
                validatePasswordUseCase(registerUiState.passwordUiState.text)
            val hasValidationError = listOf(
                nameValidation,
                emailValidationResult,
                passwordValidationResult
            ).any { it.error != null }
            if (hasValidationError) {
                registerUiState = registerUiState.copy(
                    nameUiState = registerUiState.nameUiState.copy(
                        errorMessage = nameValidation.error
                    ),
                    emailUiState = registerUiState.emailUiState.copy(
                        errorMessage = emailValidationResult.error
                    ),
                    passwordUiState = registerUiState.passwordUiState.copy(
                        errorMessage = passwordValidationResult.error
                    ),
                )
                registerUiState = registerUiState.copy(isLoading = false)
            } else {
                try {
                    val registerResult = registerUseCase(
                        registerUiState.nameUiState.text,
                        registerUiState.emailUiState.text,
                        registerUiState.passwordUiState.text
                    )

                    if (registerResult.token.isNotBlank())
                        _eventFlow.emit(UiEvent.RegisterSuccess)
                    else
                        _eventFlow.emit(UiEvent.ShowMessage("Unknown Error"))
                    registerUiState = registerUiState.copy(isLoading = false)
                } catch (e: InvalidInputTextException) {
                    registerUiState = registerUiState.copy(
                        nameUiState = registerUiState.nameUiState.copy(
                            errorMessage = validateUserNameUseCase(registerUiState.nameUiState.text).error
                        ),
                        emailUiState = registerUiState.emailUiState.copy(
                            errorMessage = validateEmailUseCase(registerUiState.emailUiState.text).error
                        ),
                        passwordUiState = registerUiState.passwordUiState.copy(
                            errorMessage = validatePasswordUseCase(registerUiState.passwordUiState.text).error
                        ),
                    )
                    registerUiState = registerUiState.copy(isLoading = false)
                } catch (e: Exception) {
                    e.printStackTrace()
                    registerUiState = registerUiState.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowMessage(e.message.toString()))
                }
            }
        }
    }
    fun onEvent(action: RegisterUiEvent) {
        registerUiState = when (action) {
            is RegisterUiEvent.NameChanged -> registerUiState.copy(
                nameUiState = registerUiState.nameUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )

            is RegisterUiEvent.EmailChanged -> registerUiState.copy(
                emailUiState = registerUiState.emailUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )

            is RegisterUiEvent.PasswordChanged -> registerUiState.copy(
                passwordUiState = registerUiState.passwordUiState.copy(
                    errorMessage = null,
                    text = action.text
                )
            )
        }
    }
    sealed class UiEvent {
        object RegisterSuccess : UiEvent()
        data class ShowMessage(var error: String) : UiEvent()
    }
}