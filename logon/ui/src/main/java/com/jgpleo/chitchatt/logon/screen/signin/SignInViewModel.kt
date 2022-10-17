package com.jgpleo.chitchatt.logon.screen.signin

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.usecase.SignInUseCase
import com.jgpleo.chitchatt.logon.error.LogonUiError
import com.jgpleo.chitchatt.logon.mapper.UserMap
import com.jgpleo.chitchatt.logon.model.User
import com.jgpleo.domain_common.usecase.result.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val screenState = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = screenState

    private val screenEmailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = screenEmailError

    private val screenPassError = MutableStateFlow(false)
    val passError: StateFlow<Boolean> = screenPassError

    fun login(email: String, pass: String) {
        val isValidEmail = checkEmail(email)
        val isValidPass = checkPass(pass)
        if (!isValidEmail || !isValidPass) return

        signInUseCase.prepare(UserCredentials(email = email, pass = pass))
            .onStart { screenState.value = State.Loading }
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        val user = UserMap.map(result.data)
                        screenState.value = State.Success(user)
                    }
                    is Either.Failure -> {
                        screenState.value = State.Error(LogonUiError.InvalidUser)
                    }
                }
            }.catch {
                screenState.value = State.Error(LogonUiError.Unknown)
            }.launchIn(viewModelScope)
    }

    private fun checkEmail(email: String): Boolean {
        return if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            screenState.value = State.Error(LogonUiError.InvalidEmail())
            screenEmailError.value = true
            false
        } else {
            screenEmailError.value = false
            true
        }
    }

    private fun checkPass(pass: String): Boolean {
        return if (pass.isEmpty()) {
            screenState.value = State.Error(LogonUiError.MandatoryField())
            screenPassError.value = true
            false
        } else {
            screenPassError.value = false
            true
        }
    }

    fun dispose() {
        screenState.value = State.Idle
    }

    sealed interface State {
        object Idle : State
        object Loading: State
        data class Success(val user: User) : State
        data class Error(val error: LogonUiError) : State
    }

}