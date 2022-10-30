package com.jgpleo.chitchatt.logon.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase
import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase.EmailStatus
import com.jgpleo.chitchatt.logon.domain.usecase.PassAndValidationAreEqualsAndValidUseCase
import com.jgpleo.chitchatt.logon.domain.usecase.PassAndValidationAreEqualsAndValidUseCase.PassStatus
import com.jgpleo.chitchatt.logon.domain.usecase.SignUpUseCase
import com.jgpleo.chitchatt.logon.mapper.UserMapper
import com.jgpleo.chitchatt.logon.model.User
import com.jgpleo.chitchatt.logon.screen.common.error.checkEmailStatus
import com.jgpleo.chitchatt.logon.screen.signup.error.checkPassStatus
import com.jgpleo.chitchatt.logon.screen.signup.error.getSignUpError
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.domain_common.usecase.result.Either
import com.jgpleo.ui_common.component.dialog.DialogModel
import com.jgpleo.ui_common.component.textfield.TextFieldError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val isEmailValidUseCase: IsEmailValidUseCase,
    private val arePassValidUseCase: PassAndValidationAreEqualsAndValidUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    private val _emailError = MutableStateFlow(TextFieldError())
    val emailError: StateFlow<TextFieldError> = _emailError
    val email = MutableStateFlow("")

    private val _passError = MutableStateFlow(TextFieldError())
    val passError: StateFlow<TextFieldError> = _passError
    val pass = MutableStateFlow("")

    private val _confirmPassError = MutableStateFlow(TextFieldError())
    val confirmPassError: StateFlow<TextFieldError> = _confirmPassError
    val confirmPass = MutableStateFlow("")

    fun register() {
        if (!fieldsAreValid()) return

        signUpUseCase.prepare(UserCredentials(email = email.value, pass = pass.value))
            .onStart { _state.value = State.Loading }
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        val user = UserMapper.map(result.data)
                        _state.value = State.Success(user)
                    }
                    is Either.Failure -> {
                        val error = getSignUpError(result.error)
                        _state.value = State.Error(error)

                        if (result.error is LogonError.UserAlreadyExists) {
                            _emailError.value = TextFieldError(
                                hasError = true,
                                errorMessage = R.string.signup_error_email_already_exists
                            )
                        }
                    }
                }
            }.catch {
                val error = getSignUpError(LogonError.Unknown)
                _state.value = State.Error(error)
            }.launchIn(viewModelScope)
    }

    private fun fieldsAreValid(): Boolean {
        return validateEmail() && validatePass()
    }

    private fun validateEmail(): Boolean {
        val emailStatus = isEmailValidUseCase(email.value)
        _emailError.value = checkEmailStatus(emailStatus)
        return emailStatus == EmailStatus.Correct
    }

    private fun validatePass(): Boolean {
        val passStatus = arePassValidUseCase(pass.value, confirmPass.value)
        when (passStatus) {
            is PassStatus.BothEmpty -> {
                _passError.value = checkPassStatus(passStatus)
                _confirmPassError.value = checkPassStatus(passStatus)
            }

            is PassStatus.PassEmpty -> {
                _passError.value = checkPassStatus(passStatus)
            }

            is PassStatus.ValidatePassEmpty,
            is PassStatus.NotEquals -> {
                _confirmPassError.value = checkPassStatus(passStatus)
            }

            is PassStatus.WeakPass -> {
                _passError.value = checkPassStatus(passStatus)
                val error = getSignUpError(LogonError.WeakPass)
                _state.value = State.Error(error)
            }

            else -> {}
        }

        return passStatus == PassStatus.Correct
    }

    fun dismissError() {
        _state.value = State.Error(DialogModel())
        _state.value = State.Idle
    }

    fun dispose() {
        _state.value = State.Idle
        email.value = ""
        _emailError.value = TextFieldError()
        pass.value = ""
        _passError.value = TextFieldError()
        confirmPass.value = ""
        _confirmPassError.value = TextFieldError()
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class Success(val user: User) : State
        data class Error(val error: DialogModel) : State
    }

}

