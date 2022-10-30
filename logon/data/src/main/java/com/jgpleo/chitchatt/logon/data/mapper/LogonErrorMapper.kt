package com.jgpleo.chitchatt.logon.data.mapper

import com.jgpleo.chitchatt.logon.data.source.FailureState
import com.jgpleo.chitchatt.logon.domain.repository.error.LogonRepositoryError

fun mapSignInError(error: FailureState): LogonRepositoryError {
    return when (error) {
        is FailureState.InvalidUser -> LogonRepositoryError.InvalidUser
        is FailureState.InvalidUserData -> LogonRepositoryError.InvalidUserData
        is FailureState.TooManyRequest -> LogonRepositoryError.Unknown
        is FailureState.Unknown -> LogonRepositoryError.Unknown
        else -> LogonRepositoryError.Unknown
    }
}

fun mapSignUpError(error: FailureState): LogonRepositoryError {
    return when (error) {
        is FailureState.InvalidUserData -> LogonRepositoryError.InvalidUserData
        is FailureState.UserAlreadyExists -> LogonRepositoryError.UserAlreadyExists
        is FailureState.TooManyRequest -> LogonRepositoryError.Unknown
        is FailureState.Unknown -> LogonRepositoryError.Unknown
        else -> LogonRepositoryError.Unknown
    }
}