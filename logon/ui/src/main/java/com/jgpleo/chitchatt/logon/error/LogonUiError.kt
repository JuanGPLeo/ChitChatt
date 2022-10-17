package com.jgpleo.chitchatt.logon.error

sealed interface LogonUiError {
    object NoError: LogonUiError
    object Unknown : LogonUiError
    object InvalidUser: LogonUiError
    object InvalidEmailOrPassFormat: LogonUiError
    object MandatoryField : LogonUiError
}