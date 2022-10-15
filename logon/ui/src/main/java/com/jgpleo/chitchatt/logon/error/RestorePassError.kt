package com.jgpleo.chitchatt.logon.error

sealed interface RestorePassError : LogonUiError {
    object InvalidEmailFormat : RestorePassError
}