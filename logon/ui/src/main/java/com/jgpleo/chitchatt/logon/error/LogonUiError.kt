package com.jgpleo.chitchatt.logon.error

sealed interface LogonUiError {
    object Unknown : LogonUiError
    object InvalidUser: LogonUiError
    data class InvalidEmail(val message: String = ""): LogonUiError
    data class MandatoryField(val message: String = "") : LogonUiError
}