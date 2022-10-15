package com.jgpleo.chitchatt.logon.error

sealed interface LogonUiError {
    object Unknown : LogonUiError
    object InvalidUser: LogonUiError
}