package com.jgpleo.chitchatt.logon.domain.error

import com.jgpleo.domain_common.usecase.result.DomainError

sealed interface LogonError : DomainError {
    object InvalidUser : LogonError
}