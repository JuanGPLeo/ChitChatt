package com.jgpleo.chitchatt.logon.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase
import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase.EmailStatus
import com.jgpleo.chitchatt.logon.domain.usecase.IsPassValidUseCase
import com.jgpleo.chitchatt.logon.domain.usecase.IsPassValidUseCase.PassStatus
import com.jgpleo.chitchatt.logon.domain.usecase.SignInUseCase
import com.jgpleo.chitchatt.logon.mapper.UserMapper
import com.jgpleo.chitchatt.logon.model.User
import com.jgpleo.chitchatt.logon.screen.signin.error.checkEmailStatus
import com.jgpleo.chitchatt.logon.screen.signin.error.checkPassStatus
import com.jgpleo.domain_common.usecase.result.Either
import com.jgpleo.ui_common.component.dialog.DialogModel
import com.jgpleo.ui_common.component.textfield.TextFieldError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val isPassValidUseCase: IsPassValidUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private val _screenError = MutableStateFlow(DialogModel())
    val errorState: StateFlow<DialogModel> = _screenError

    private val _emailError = MutableStateFlow(TextFieldError())
    val emailError: StateFlow<TextFieldError> = _emailError

    private val _passError = MutableStateFlow(TextFieldError())
    val passError: StateFlow<TextFieldError> = _passError

    fun login(email: String, pass: String) {
        if (!fieldsAreValid(email, pass)) return

        signInUseCase.prepare(UserCredentials(email = email, pass = pass))
            .onStart { _state.value = State.Loading }
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        val user = UserMapper.map(result.data)
                        _state.value = State.Success(user)
                    }
                    is Either.Failure -> {
//                        screenState.value = State.Error(LogonUiError.InvalidUser)
                    }
                }
            }.catch {
//                screenState.value = State.Error(LogonUiError.Unknown)
            }.launchIn(viewModelScope)
    }

    private fun fieldsAreValid(email: String, pass: String): Boolean {
        val emailStatus = isEmailValidUseCase(email)
        _emailError.value = checkEmailStatus(emailStatus)

        val passStatus = isPassValidUseCase(pass)
        _passError.value = checkPassStatus(passStatus)

        return emailStatus == EmailStatus.Correct &&
                passStatus == PassStatus.Correct
    }

    fun dismissError() {
        _screenError.value = DialogModel()
    }

    fun dispose() {
        _state.value = State.Idle
        _emailError.value = TextFieldError()
        _passError.value = TextFieldError()
    }

    sealed interface State {
        object Idle : State
        object Loading: State
        data class Success(val user: User) : State
        data class Error(val error: String) : State
    }

}