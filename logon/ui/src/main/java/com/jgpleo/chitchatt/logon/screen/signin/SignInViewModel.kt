package com.jgpleo.chitchatt.logon.screen.signin

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

    fun login(email: String, pass: String) {
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