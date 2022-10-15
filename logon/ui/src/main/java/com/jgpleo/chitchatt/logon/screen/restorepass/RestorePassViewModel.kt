package com.jgpleo.chitchatt.logon.screen.restorepass

import androidx.lifecycle.ViewModel
import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.usecase.RestorePassUseCase
import com.jgpleo.chitchatt.logon.error.LogonUiError
import com.jgpleo.chitchatt.logon.error.RestorePassError
import com.jgpleo.domain_common.usecase.result.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RestorePassViewModel @Inject constructor(
    private val restorePassUseCase: RestorePassUseCase
) : ViewModel() {

    private val screenState = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = screenState

    fun restorePass(email: String) {
        restorePassUseCase.prepare(EmailRestorePass(email))
            .onStart { screenState.value = State.Loading }
            .onEach { result ->
                when (result) {
                    is Either.Success -> {
                        screenState.value = State.Success
                    }
                    is Either.Failure -> {
                        screenState.value = State.Error(RestorePassError.InvalidEmailFormat)
                    }
                }
            }.catch {
                screenState.value = State.Error(LogonUiError.Unknown)
            }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        object Success : State
        data class Error(val error: LogonUiError) : State
    }
}