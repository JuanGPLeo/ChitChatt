package com.jgpleo.chitchatt.logon.domain.repository.error

import com.jgpleo.domain_common.usecase.repository.RepositoryError

sealed interface LogonRepositoryError : RepositoryError {
    object InvalidUser: LogonRepositoryError
    object InvalidUserData : LogonRepositoryError
    object Unknown : LogonRepositoryError
}