package com.jgpleo.chitchatt.logon.screen.restorepass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase
import com.jgpleo.chitchatt.logon.domain.usecase.RestorePassUseCase
import com.jgpleo.chitchatt.logon.screen.common.error.checkEmailStatus
import com.jgpleo.chitchatt.logon.screen.restorepass.error.getRestorePassError
import com.jgpleo.domain_common.usecase.result.Either
import com.jgpleo.ui_common.component.dialog.DialogModel
import com.jgpleo.ui_common.component.textfield.TextFieldError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RestorePassViewModel @Inject constructor(
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val restorePassUseCase: RestorePassUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private val _emailError = MutableStateFlow(TextFieldError())
    val emailError: StateFlow<TextFieldError> = _emailError
    val email = MutableStateFlow("")

    fun restorePass() {
        if (!isValidPass()) return

        restorePassUseCase.prepare(EmailRestorePass(email.value))
            .onStart { _state.value = State.Loading }
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        _state.value = State.Success
                    }
                    is Either.Failure -> {
                        val error = getRestorePassError(result.error)
                        _state.value = State.Error(error)
                    }
                }
            }.catch {
                val error = getRestorePassError(LogonError.Unknown)
                _state.value = State.Error(error)
            }.launchIn(viewModelScope)
    }

    private fun isValidPass(): Boolean {
        val emailStatus = isEmailValidUseCase(email.value)
        _emailError.value = checkEmailStatus(emailStatus)
        return emailStatus == IsEmailValidUseCase.EmailStatus.Correct
    }

    fun dismissError() {
        _state.value = State.Error(DialogModel())
        _state.value = State.Idle
    }

    fun dispose() {
        _state.value = State.Idle
        email.value = ""
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        object Success : State
        data class Error(val error: DialogModel) : State
    }
}